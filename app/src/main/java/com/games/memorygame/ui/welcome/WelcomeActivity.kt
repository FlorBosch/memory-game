package com.games.memorygame.ui.welcome

import android.content.Intent
import android.os.Bundle

import com.games.memorygame.R
import com.games.memorygame.model.game.BoardConfiguration
import com.games.memorygame.ui.BaseActivity
import com.games.memorygame.ui.board.BoardActivity
import com.games.memorygame.ui.score.ScoreActivity

import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_welcome.level_options
import kotlinx.android.synthetic.main.activity_welcome.player_mode_options
import kotlinx.android.synthetic.main.activity_welcome.btn_start
import kotlinx.android.synthetic.main.activity_welcome.btn_scores


class WelcomeActivity : BaseActivity() {

    @Inject lateinit var boardConfiguration: BoardConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        activityComponent().inject(this)
        setUpConfig()
    }

    private fun setUpConfig() {
        setLevelValue(level_options.checkedRadioButtonId)
        setPlayerModelValue(player_mode_options.checkedRadioButtonId)
        level_options.setOnCheckedChangeListener { _, id -> setLevelValue(id) }
        player_mode_options.setOnCheckedChangeListener { _, id -> setPlayerModelValue(id) }
        btn_start.setOnClickListener { startActivity(Intent(this, BoardActivity::class.java)) }
        btn_scores.setOnClickListener { startActivity(Intent(this, ScoreActivity::class.java)) }
    }

    private fun setLevelValue(checkedId: Int) {
        boardConfiguration.level = BoardConfigurationMapper.getLevelMode(checkedId)
    }

    private fun setPlayerModelValue(checkedId: Int) {
        boardConfiguration.playerMode = BoardConfigurationMapper.getPlayerMode(checkedId)
    }

}
