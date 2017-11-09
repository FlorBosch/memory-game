package com.games.memorygame.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.widget.RadioGroup

import com.games.memorygame.R
import com.games.memorygame.model.game.BoardConfiguration
import com.games.memorygame.ui.BaseActivity
import com.games.memorygame.ui.board.BoardActivity
import com.games.memorygame.ui.score.ScoreActivity

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

class WelcomeActivity : BaseActivity(), WelcomeMvpView {

    @Inject lateinit var presenter: WelcomePresenter

    @Inject lateinit var boardConfiguration: BoardConfiguration

    @BindView(R.id.level_options) lateinit var levelOptions: RadioGroup

    @BindView(R.id.player_mode_options) lateinit var playerModeOptions: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        activityComponent()!!.inject(this)
        ButterKnife.bind(this)
        presenter.attachView(this)
        setUpConfig()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    @OnClick(R.id.btn_start)
    fun onStartClick() {
        startActivity(Intent(this, BoardActivity::class.java))
    }

    @OnClick(R.id.btn_scores)
    fun onScoreClick() {
        startActivity(Intent(this, ScoreActivity::class.java))
    }

    private fun setUpConfig() {
        setLevelValue(levelOptions.checkedRadioButtonId)
        setPlayerModelValue(playerModeOptions.checkedRadioButtonId)
        levelOptions.setOnCheckedChangeListener { _, id -> setLevelValue(id) }
        playerModeOptions.setOnCheckedChangeListener { _, id -> setPlayerModelValue(id) }
    }

    private fun setLevelValue(checkedId: Int) {
        boardConfiguration.level = BoardConfigurationMapper.getLevelMode(checkedId)
    }

    private fun setPlayerModelValue(checkedId: Int) {
        boardConfiguration.playerMode = BoardConfigurationMapper.getPlayerMode(checkedId)
    }

}
