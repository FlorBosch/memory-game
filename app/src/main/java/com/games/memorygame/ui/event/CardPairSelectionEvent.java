package com.games.memorygame.ui.event;


public class CardPairSelectionEvent {

    private final boolean matches;

    public CardPairSelectionEvent(boolean matches) {
        this.matches = matches;
    }

    public boolean isMatch() {
        return matches;
    }
}
