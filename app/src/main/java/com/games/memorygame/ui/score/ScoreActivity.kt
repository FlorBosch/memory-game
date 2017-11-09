package com.games.memorygame.ui.score

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar

import android.support.v7.widget.DividerItemDecoration.VERTICAL

import com.games.memorygame.R
import com.games.memorygame.model.Score
import com.games.memorygame.ui.BaseActivity

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife

class ScoreActivity : BaseActivity(), ScoreMvpView {

    @BindView(R.id.score_list) lateinit var scoresView: RecyclerView

    @Inject lateinit var presenter: ScorePresenter

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        activityComponent()!!.inject(this)
        ButterKnife.bind(this)
        presenter.attachView(this)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        scoresView.layoutManager = LinearLayoutManager(this)
        scoresView.addItemDecoration(DividerItemDecoration(scoresView.context, VERTICAL))
        presenter.loadScores()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun displayScores(scores: List<Score>) {
        scoresView.swapAdapter(ScoreListViewAdapter(scores), false)
    }

    override fun onError() {
        AlertDialog.Builder(this)
                .setMessage(R.string.error_loading_scores)
                .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
    }
}