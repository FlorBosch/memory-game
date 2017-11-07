package com.games.memorygame.ui.score;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

import com.games.memorygame.R;
import com.games.memorygame.model.Score;
import com.games.memorygame.ui.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScoreActivity extends BaseActivity implements ScoreMvpView {

    @BindView(R.id.score_list)
    RecyclerView scoresView;

    @Inject
    ScorePresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        activityComponent().inject(this);
        ButterKnife.bind(this);
        presenter.attachView(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        scoresView.setLayoutManager(new LinearLayoutManager(this));
        scoresView.addItemDecoration(new DividerItemDecoration(scoresView.getContext(), VERTICAL));
        presenter.loadScores();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void displayScores(List<Score> scores) {
        scoresView.swapAdapter(new ScoreListViewAdapter(scores), false);
    }

    @Override
    public void onError() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.error_loading_scores)
                .setPositiveButton(R.string.ok, (dialog, i) -> dialog.dismiss())
                .create()
                .show();
    }
}