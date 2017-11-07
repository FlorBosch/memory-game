package com.games.memorygame;


import com.games.memorygame.model.Score;
import com.games.memorygame.persistence.ScoreDataSource;
import com.games.memorygame.ui.score.ScoreMvpView;
import com.games.memorygame.ui.score.ScorePresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScorePresenterTest {

    @Mock
    ScoreMvpView mockScoreMvpView;

    @Mock
    ScoreDataSource mockScoreDataSource;

    private ScorePresenter scorePresenter;

    @Rule
    public final RxSchedulersRule rule = new RxSchedulersRule();

    @Before
    public void setUp() {
        scorePresenter = new ScorePresenter(mockScoreDataSource);
        scorePresenter.attachView(mockScoreMvpView);
    }

    @After
    public void tearDown() {
        scorePresenter.detachView();
    }

    @Test
    public void loadScores() {
        List<Score> scores = getScores();
        when(mockScoreDataSource.loadScores())
                .thenReturn(Flowable.just(scores));

        scorePresenter.loadScores();
        verify(mockScoreMvpView).displayScores(scores);
        verify(mockScoreMvpView, never()).onError();
    }

    @Test
    public void loadScoresFails() {
        when(mockScoreDataSource.loadScores())
                .thenReturn(Flowable.error(new Throwable()));

        scorePresenter.loadScores();
        verify(mockScoreMvpView).onError();
        verify(mockScoreMvpView, never()).displayScores(new ArrayList<>());
    }

    private List<Score> getScores() {
        ArrayList<Score> scores = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            scores.add(new Score("Player " + i, i));
        }
        return scores;
    }
}

