package com.games.memorygame.ui.board;


import com.games.memorygame.model.Photo;
import com.games.memorygame.model.game.Player;
import com.games.memorygame.ui.MvpView;

import java.util.List;

public interface BoardMvpView extends MvpView {

    void showDashboard(List<Photo> photos, int rows, int columns);
    void onError(String errorMessage);
    void onNetworkError();
    void updateScore(List<Player> players);
    void updatePlayerTurn(List<Player> players);
    void setUpTimer(int seconds);
}
