package com.games.memorygame.ui.board;

import com.games.memorygame.model.game.BoardConfiguration;
import com.games.memorygame.model.game.GameStatus;
import com.games.memorygame.model.Photo;
import com.games.memorygame.model.Score;
import com.games.memorygame.network.ImageDownloader;
import com.games.memorygame.network.MemoryGameService;
import com.games.memorygame.persistence.ScoreDataSource;
import com.games.memorygame.ui.BasePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

import static com.games.memorygame.util.Constants.PHOTO_THEME;
import static com.games.memorygame.util.RxUtil.call;

public class BoardPresenter extends BasePresenter<BoardMvpView> {

    private MemoryGameService service;
    private ScoreDataSource scoreLocalDataSource;
    private ImageDownloader imageDownloader;
    private GameStatus gameStatus;

    @Inject
    public BoardPresenter(BoardConfiguration boardConfiguration,
                          MemoryGameService service,
                          ScoreDataSource scoreDataSource,
                          ImageDownloader imageDownloader) {
        this.gameStatus = new GameStatus(boardConfiguration);
        this.service = service;
        this.scoreLocalDataSource = scoreDataSource;
        this.imageDownloader = imageDownloader;
    }

    public void loadBoard() {
        assertViewAttached();
        addDisposable(call(service.getPhotos(gameStatus.getNumberOfCards() / 2, PHOTO_THEME))
                .subscribe(
                        response -> {
                            if (!response.hasError()) {
                                downloadPhotos(response.getPhotos());
                                return;
                            }
                            String error = response.getErrorMessage();
                            Timber.e(error);
                            getView().onError(error);
                        }, throwable -> {
                            Timber.e(throwable, throwable.getMessage());
                            getView().onNetworkError();
                        }));
    }

    public void onMatchEvent(boolean isMatch) {
        assertViewAttached();
        if (isMatch) {
            gameStatus.addMatchPoint();
            getView().updateScore(gameStatus.getPlayers());
            return;
        }
        gameStatus.changeTurn();
        getView().updatePlayerTurn(gameStatus.getPlayers());
    }

    public void saveScore(String userName) {
        int score = gameStatus.getWinner().getScore();
        call(() -> scoreLocalDataSource.addScore(new Score(userName, score))).subscribe();
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    private void downloadPhotos(List<Photo> photos) {
        addDisposable(call(() -> imageDownloader.downloadPhotos(photos))
                .subscribe(() -> showBoard(photos)
                        , throwable -> {
                            Timber.e(throwable, throwable.getMessage());
                            getView().onNetworkError();
                        })
        );
    }

    private void showBoard(List<Photo> photos) {
        BoardConfiguration configuration = gameStatus.getBoardConfiguration();
        if (!configuration.isMultiPLayerMode()) {
            getView().setUpTimer(configuration.getLevel().getSeconds());
        }
        photos.addAll(new ArrayList<>(photos));
        Collections.shuffle(photos);
        getView().showDashboard(photos, configuration.getRows(), configuration.getColumns());
        getView().updatePlayerTurn(gameStatus.getPlayers());
        getView().updateScore(gameStatus.getPlayers());
    }

}
