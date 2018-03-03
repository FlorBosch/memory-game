package com.games.memorygame.ui.score

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager

import android.support.v7.widget.DividerItemDecoration.VERTICAL

import com.games.memorygame.R
import com.games.memorygame.model.Score
import com.games.memorygame.ui.BaseActivity

import javax.inject.Inject

import kotlinx.android.synthetic.main.activity_score.toolbar
import kotlinx.android.synthetic.main.activity_score.score_list


class ScoreActivity : BaseActivity(), ScoreMvpView {

    @Inject lateinit var presenter: ScorePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        activityComponent()!!.inject(this)
        presenter.attachView(this)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        score_list.layoutManager = LinearLayoutManager(this)
        score_list.addItemDecoration(DividerItemDecoration(score_list.context, VERTICAL))
        presenter.loadScores()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun displayScores(scores: List<Score>) {
        score_list.swapAdapter(ScoreListViewAdapter(scores), false)
    }

    override fun onError() {
        AlertDialog.Builder(this)
                .setMessage(R.string.error_loading_scores)
                .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
    }
}