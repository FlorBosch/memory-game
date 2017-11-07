package com.games.memorygame.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.games.memorygame.R;
import com.games.memorygame.model.game.BoardConfiguration;
import com.games.memorygame.ui.BaseActivity;
import com.games.memorygame.ui.board.BoardActivity;
import com.games.memorygame.ui.score.ScoreActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends BaseActivity implements WelcomeMvpView {

    @Inject
    WelcomePresenter presenter;

    @Inject
    BoardConfiguration boardConfiguration;

    @BindView(R.id.level_options)
    RadioGroup levelOptions;

    @BindView(R.id.player_mode_options)
    RadioGroup playerModeOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        activityComponent().inject(this);
        ButterKnife.bind(this);
        presenter.attachView(this);
        setUpConfig();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick(R.id.btn_start)
    public void onStartClick() {
        startActivity(new Intent(this, BoardActivity.class));
    }

    @OnClick(R.id.btn_scores)
    public void onScoreClick() {
        startActivity(new Intent(this, ScoreActivity.class));
    }

    private void setUpConfig() {
        setLevelValue(levelOptions.getCheckedRadioButtonId());
        setPlayerModelValue(playerModeOptions.getCheckedRadioButtonId());
        levelOptions.setOnCheckedChangeListener((group, id) -> setLevelValue(id));
        playerModeOptions.setOnCheckedChangeListener((group, id) -> setPlayerModelValue(id));
    }

    private void setLevelValue(int checkedId) {
        boardConfiguration.setLevel(BoardConfigurationMapper.getLevelMode(checkedId));
    }

    private void setPlayerModelValue(int checkedId) {
        boardConfiguration.setPlayerMode(BoardConfigurationMapper.getPlayerMode(checkedId));
    }

}
