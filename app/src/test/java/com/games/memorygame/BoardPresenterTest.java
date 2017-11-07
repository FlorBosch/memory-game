package com.themobilecompany.memorygame;


import com.themobilecompany.memorygame.model.Photo;
import com.themobilecompany.memorygame.model.Photos;
import com.themobilecompany.memorygame.model.game.BoardConfiguration;
import com.themobilecompany.memorygame.model.game.GameStatus;
import com.themobilecompany.memorygame.model.game.PlayerMode;
import com.themobilecompany.memorygame.network.FlickrResponse;
import com.themobilecompany.memorygame.network.ImageDownloader;
import com.themobilecompany.memorygame.network.MemoryGameService;
import com.themobilecompany.memorygame.persistence.ScoreDataSource;
import com.themobilecompany.memorygame.ui.board.BoardMvpView;
import com.themobilecompany.memorygame.ui.board.BoardPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.themobilecompany.memorygame.util.Constants.PHOTO_THEME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BoardPresenterTest {

    @Mock
    BoardMvpView mockBoardMvpView;

    @Mock
    MemoryGameService mockMemoryGameService;

    @Mock
    ScoreDataSource mockScoreDataSource;

    @Mock
    ImageDownloader mockImageDownloader;

    private BoardConfiguration boardConfiguration;
    private BoardPresenter boardPresenter;

    @Rule
    public final RxSchedulersRule rule = new RxSchedulersRule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        boardConfiguration = new BoardConfiguration();
        boardConfiguration.setPlayerMode(PlayerMode.MULTI_PLAYER);
        boardPresenter = new BoardPresenter(boardConfiguration, mockMemoryGameService,
                mockScoreDataSource, mockImageDownloader);
        boardPresenter.attachView(mockBoardMvpView);
    }

    @After
    public void tearDown() {
        boardPresenter.detachView();
    }

    @Test
    public void loadBoard() {
        GameStatus gameStatus = boardPresenter.getGameStatus();
        int numberOfPhotos = gameStatus.getNumberOfCards() / 2;
        FlickrResponse response = getFlickrResponse(numberOfPhotos);
        when(mockMemoryGameService.getPhotos(numberOfPhotos, PHOTO_THEME))
                .thenReturn(Observable.just(response));

        boardPresenter.loadBoard();
        verify(mockBoardMvpView).showDashboard(response.getPhotos(),
                boardConfiguration.getRows(), boardConfiguration.getColumns());
        verify(mockBoardMvpView).updateScore(gameStatus.getPlayers());
        verify(mockBoardMvpView).updatePlayerTurn(gameStatus.getPlayers());
        verify(mockBoardMvpView, never()).onNetworkError();
    }

    @Test
    public void loadBoardFails() {
        GameStatus gameStatus = boardPresenter.getGameStatus();
        when(mockMemoryGameService.getPhotos(gameStatus.getNumberOfCards() / 2, PHOTO_THEME))
                .thenReturn(Observable.error(new Throwable()));

        boardPresenter.loadBoard();
        verify(mockBoardMvpView).onNetworkError();
        verify(mockBoardMvpView, never()).showDashboard(new ArrayList<>(),
                boardConfiguration.getRows(), boardConfiguration.getColumns());
    }

    @Test
    public void onMatchEvent() {
        GameStatus gameStatus = boardPresenter.getGameStatus();
        assertThat(gameStatus.getPlayerTurn(), equalTo(gameStatus.getPlayers().get(0)));
        boardPresenter.onMatchEvent(true);
        assertThat(gameStatus.getPlayerTurn(), equalTo(gameStatus.getPlayers().get(0)));
        assertThat(gameStatus.getPlayers().get(0).getScore(), is(10));
        assertThat(gameStatus.getPlayers().get(1).getScore(), is(0));
        boardPresenter.onMatchEvent(false);
        assertThat(gameStatus.getPlayerTurn(), equalTo(gameStatus.getPlayers().get(1)));
    }

    private FlickrResponse getFlickrResponse(int numberOfPhotos) {
        List<Photo> photos = new ArrayList<>();
        for (int i = 0; i < numberOfPhotos; i++) {
            photos.add(new Photo("Title " + i, i, "", "", String.valueOf(i)));
        }
        return new FlickrResponse(new Photos(photos), "ok", null, "");
    }

}

