package com.games.memorygame.injection

import android.content.Context

import com.games.memorygame.MemoryGameApplication
import com.games.memorygame.injection.component.DaggerTestComponent
import com.games.memorygame.injection.component.TestComponent
import com.games.memorygame.injection.module.ApplicationTestModule

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

import java.util.concurrent.TimeUnit

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import java.util.concurrent.Executor

class TestComponentRule(private val context: Context) : TestRule {

    private val immediate = object : Scheduler() {
        override fun scheduleDirect(@NonNull run: Runnable, delay: Long,
                                    @NonNull unit: TimeUnit): Disposable {
            return super.scheduleDirect(run, 0, unit)
        }

        override fun createWorker(): Scheduler.Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
        }
    }

    private val testComponent: TestComponent

    init {
        testComponent = DaggerTestComponent.builder()
                .applicationTestModule(
                        ApplicationTestModule(MemoryGameApplication[context]))
                .build()
    }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setInitIoSchedulerHandler { _ -> immediate }
                RxJavaPlugins.setInitComputationSchedulerHandler { _ -> immediate }
                RxJavaPlugins.setInitNewThreadSchedulerHandler { _ -> immediate }
                RxJavaPlugins.setInitSingleSchedulerHandler { _ -> immediate }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> immediate }

                try {
                    val application = MemoryGameApplication[context]
                    application.component = testComponent
                    base.evaluate()
                    application.component = null
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}
