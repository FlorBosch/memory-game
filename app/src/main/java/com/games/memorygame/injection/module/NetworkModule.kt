package com.games.memorygame.injection.module

import com.games.memorygame.BuildConfig
import com.games.memorygame.network.MemoryGameService

import java.io.File
import java.util.Locale

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber

import java.lang.String.format


@Module
class NetworkModule(cacheFile: File) {

    private val cacheFile: File

    init {
        this.cacheFile = File(cacheFile, "responses")
    }

    @Provides
    @Singleton
    internal fun provideCall(): Retrofit {
        var cache: Cache? = null
        try {
            cache = Cache(cacheFile, (10 * 1024 * 1024).toLong())
        } catch (e: Exception) {
            Timber.e(e, e.message)
        }

        val maxAge = format(Locale.getDefault(), "max-age=%d", BuildConfig.CACHE_TIME)

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    var request = chain.request().newBuilder()
                            .header("Content-Type", "application/json")
                            .removeHeader("Pragma")
                            .header("Cache-Control", maxAge)
                            .build()

                    val url = request.url().newBuilder()
                            .addQueryParameter("api_key", BuildConfig.API_KEY)
                            .addQueryParameter("format", "json")
                            .addQueryParameter("nojsoncallback", "1")
                            .build()
                    request = request.newBuilder().url(url).build()

                    val response = chain.proceed(request)
                    response.cacheResponse()
                    response
                }
                .cache(cache)
                .build()

        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun providesMemoryService(retrofit: Retrofit): MemoryGameService = retrofit.create(MemoryGameService::class.java)

}