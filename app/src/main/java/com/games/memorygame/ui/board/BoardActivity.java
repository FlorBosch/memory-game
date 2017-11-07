package com.themobilecompany.memorygame.ui.board;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.themobilecompany.memorygame.R;
import com.themobilecompany.memorygame.model.Photo;
import com.themobilecompany.memorygame.model.game.Player;
import com.themobilecompany.memorygame.ui.BaseActivity;
import com.themobilecompany.memorygame.ui.event.CompletionEvent;
import com.themobilecompany.memorygame.ui.event.CardPairSelectionEvent;
import com.themobilecompany.memorygame.ui.widget.BoardView;
import com.themobilecompany.memorygame.ui.widget.DataContainerLayout;
import com.themobilecompany.memorygame.ui.board.SaveScoreDialogFragment.OnCompleteListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
import static android.view.View.VISIBLE;
import static com.themobilecompany.memorygame.util.Constants.SAVE_SCORE_DIALOG_KEY;
import static com.themobilecompany.memorygame.util.DateTimeUtil.fromMilliSecondsToString;

public class BoardActivity extends BaseActivity implements BoardMvpView, OnCompleteListener {

    @Inject
    BoardPresenter presenter;

    @BindView(R.id.board_layout)
    BoardView boardView;

    @BindView(R.id.data_container)
    DataContainerLayout dataContainer;

    @BindView(R.id.timer)
    TextView timerView;

    @BindView(R.id.first_player_score)
    TextView firstPlayerScoreView;

    @BindView(R.id.second_player_score)
    TextView secondPlayerScoreView;

    @BindBool(R.bool.is_tablet)
    boolean isTablet;

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        activityComponent().inject(this);
        ButterKnife.bind(this);
        setRequestedOrientation(isTablet ?
                SCREEN_ORIENTATION_LANDSCAPE : SCREEN_ORIENTATION_PORTRAIT);
        presenter.attachView(this);
        EventBus.getDefault().register(this);
        dataContainer.setUp(presenter::loadBoard);
        presenter.loadBoard();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (timer != null) {
            timer.cancel();
        }
        presenter.detachView();
    }

    @Override
    public void onBackPressed() {
        showSaveScoreDialog();
    }

    @Override
    public void showDashboard(List<Photo> photos, int rows, int columns) {
        boardView.setUp(photos, rows, columns);
        dataContainer.stopLoading();
    }

    @Override
    public void onNetworkError() {
        dataContainer.displayError();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMatchEvent(CardPairSelectionEvent event) {
        presenter.onMatchEvent(event.isMatch());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCompletionEvent(CompletionEvent event) {
        if (timer != null) {
            timer.cancel();
        }
        new AlertDialog.Builder(this)
                .setTitle(R.string.congratulations_title)
                .setMessage(R.string.continue_playing)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, (dialog, i) -> presenter.loadBoard())
                .setNegativeButton(R.string.no, (dialog, i) -> showSaveScoreDialog())
                .create()
                .show();
    }

    @Override
    public void updateScore(List<Player> players) {
        firstPlayerScoreView.setText(
                getString(R.string.player_score_label, 1, players.get(0).getScore()));
        if (players.size() == 2) {
            secondPlayerScoreView.setVisibility(VISIBLE);
            secondPlayerScoreView.setText(
                    getString(R.string.player_score_label, 2, players.get(1).getScore()));
        }
    }

    @Override
    public void updatePlayerTurn(List<Player> players) {
        firstPlayerScoreView.setSelected(players.get(0).hasTurn());
        if (players.size() == 2) {
            secondPlayerScoreView.setSelected(players.get(1).hasTurn());
        }
    }

    @Override
    public void setUpTimer(int seconds) {
        timer = new CountDownTimer(seconds * 1000, 1000) {

            public void onTick(long millis) {
                timerView.setVisibility(VISIBLE);
                timerView.setText(fromMilliSecondsToString(BoardActivity.this, millis));
            }

            public void onFinish() {
                timer.cancel();
                showOnTimeFinishedDialog();
            }
        };
        timer.start();
    }

    @Override
    public void onComplete(String userName) {
        if (userName != null && !userName.isEmpty()) {
            presenter.saveScore(userName);
        }
        super.onBackPressed();
    }

    @Override
    public void onError(String errorMessage) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(errorMessage)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, (dialog, i) -> super.onBackPressed())
                .create()
                .show();
    }

    private void showSaveScoreDialog() {
        new SaveScoreDialogFragment().show(getSupportFragmentManager(), SAVE_SCORE_DIALOG_KEY);
    }

    private void showOnTimeFinishedDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.time_finished_title)
                .setMessage(R.string.save_match)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, (dialog, i) -> showSaveScoreDialog())
                .setNegativeButton(R.string.no, (dialog, i) -> super.onBackPressed())
                .create()
                .show();
    }
}
