package com.themobilecompany.memorygame.ui.board;


import com.themobilecompany.memorygame.model.Photo;
import com.themobilecompany.memorygame.model.game.Player;
import com.themobilecompany.memorygame.ui.MvpView;

import java.util.List;

public interface BoardMvpView extends MvpView {

    void showDashboard(List<Photo> photos, int rows, int columns);
    void onError(String errorMessage);
    void onNetworkError();
    void updateScore(List<Player> players);
    void updatePlayerTurn(List<Player> players);
    void setUpTimer(int seconds);
}
