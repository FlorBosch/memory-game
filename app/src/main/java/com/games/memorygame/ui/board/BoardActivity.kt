package com.games.memorygame.ui.board

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AlertDialog

import com.games.memorygame.R
import com.games.memorygame.model.Photo
import com.games.memorygame.model.game.Player
import com.games.memorygame.ui.BaseActivity
import com.games.memorygame.ui.event.CompletionEvent
import com.games.memorygame.ui.event.CardPairSelectionEvent
import com.games.memorygame.ui.board.SaveScoreDialogFragment.OnCompleteListener

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

import javax.inject.Inject

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.view.View.VISIBLE
import com.games.memorygame.util.Constants
import com.games.memorygame.util.DateTimeUtil.fromMilliSecondsToString

import kotlinx.android.synthetic.main.activity_board.board_layout
import kotlinx.android.synthetic.main.activity_board.data_container
import kotlinx.android.synthetic.main.activity_board.timer
import kotlinx.android.synthetic.main.activity_board.first_player_score
import kotlinx.android.synthetic.main.activity_board.second_player_score


class BoardActivity : BaseActivity(), BoardMvpView, OnCompleteListener {

    @Inject lateinit var presenter: BoardPresenter

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        activityComponent()!!.inject(this)
        requestedOrientation = if (resources.getBoolean(R.bool.is_tablet))
            SCREEN_ORIENTATION_LANDSCAPE
        else
            SCREEN_ORIENTATION_PORTRAIT
        presenter.attachView(this)
        EventBus.getDefault().register(this)
        data_container.setUp { presenter.loadBoard() }
        presenter.loadBoard()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
        presenter.detachView()
    }

    override fun onBackPressed() {
        showSaveScoreDialog()
    }

    override fun showDashboard(photos: List<Photo>, rows: Int, columns: Int) {
        board_layout.setUp(photos, rows, columns)
        data_container.stopLoading()
    }

    override fun onNetworkError() {
        data_container.displayError()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMatchEvent(event: CardPairSelectionEvent) {
        presenter.onMatchEvent(event.isMatch)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCompletionEvent(event: CompletionEvent) {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
        AlertDialog.Builder(this)
                .setTitle(R.string.congratulations_title)
                .setMessage(R.string.continue_playing)
                .setCancelable(false)
                .setPositiveButton(R.string.yes) { _, _ -> presenter.loadBoard() }
                .setNegativeButton(R.string.no) { _, _ -> showSaveScoreDialog() }
                .create()
                .show()
    }

    override fun updateScore(players: List<Player>) {
        first_player_score.text = getString(R.string.player_score_label, 1, players[0].score)
        if (players.size == 2) {
            second_player_score.visibility = VISIBLE
            second_player_score.text = getString(R.string.player_score_label, 2, players[1].score)
        }
    }

    override fun updatePlayerTurn(players: List<Player>) {
        first_player_score.isSelected = players[0].hasTurn()
        if (players.size == 2) {
            second_player_score.isSelected = players[1].hasTurn()
        }
    }

    override fun setUpTimer(seconds: Int) {
        countDownTimer = object : CountDownTimer((seconds * 1000).toLong(), 1000) {

            override fun onTick(millis: Long) {
                timer.visibility = VISIBLE
                timer.text = fromMilliSecondsToString(this@BoardActivity, millis)
            }

            override fun onFinish() {
                countDownTimer!!.cancel()
                showOnTimeFinishedDialog()
            }
        }
        countDownTimer!!.start()
    }

    override fun onComplete(userName: String?) {
        if (userName != null && !userName.isEmpty()) {
            presenter.saveScore(userName)
        }
        super.onBackPressed()
    }

    override fun onError(errorMessage: String) {
        AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(errorMessage)
                .setCancelable(false)
                .setPositiveButton(R.string.ok) { _, _ -> super.onBackPressed() }
                .create()
                .show()
    }

    private fun showSaveScoreDialog() {
        SaveScoreDialogFragment().show(supportFragmentManager, Constants.SAVE_SCORE_DIALOG_KEY)
    }

    private fun showOnTimeFinishedDialog() {
        AlertDialog.Builder(this)
                .setTitle(R.string.time_finished_title)
                .setMessage(R.string.save_match)
                .setCancelable(false)
                .setPositiveButton(R.string.yes) { _, _ -> showSaveScoreDialog() }
                .setNegativeButton(R.string.no) { _, _ -> super.onBackPressed() }
                .create()
                .show()
    }
}
