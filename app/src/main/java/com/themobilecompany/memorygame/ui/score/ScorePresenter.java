package com.themobilecompany.memorygame.ui.score;

import com.themobilecompany.memorygame.persistence.ScoreDataSource;
import com.themobilecompany.memorygame.ui.BasePresenter;

import javax.inject.Inject;

import timber.log.Timber;

import static com.themobilecompany.memorygame.util.RxUtil.call;


public class ScorePresenter extends BasePresenter<ScoreMvpView> {

    private ScoreDataSource scoreLocalDataSource;

    @Inject
    public ScorePresenter(ScoreDataSource scoreDataSource) {
        this.scoreLocalDataSource = scoreDataSource;
    }

    public void loadScores() {
        assertViewAttached();
        addDisposable(call(scoreLocalDataSource.loadScores()).subscribe(
                scores -> getView().displayScores(scores),
                throwable -> {
                    Timber.e(throwable, throwable.getMessage());
                    getView().onError();
                })
        );
    }

}
