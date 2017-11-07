package com.games.memorygame.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.support.v7.widget.GridLayout;

import com.games.memorygame.R;
import com.games.memorygame.model.Photo;
import com.games.memorygame.ui.event.CompletionEvent;
import com.games.memorygame.ui.event.CardPairSelectionEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class BoardView extends GridLayout {

    List<BoardCardView> flippedCards = new ArrayList<>();
    private int cardsToFlip = 0;

    public BoardView(Context context) {
        super(context);
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setUp(List<Photo> photos, int rows, int columns) {
        cardsToFlip = photos.size();
        setOrientation(HORIZONTAL);
        setColumnCount(columns);
        setRowCount(rows);
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                addView(buildView(photos.get(row * (rows - 1) + column), row, column));
            }
        }
    }

    private boolean canFlip(BoardCardView view) {
        return flippedCards.size() < 2 && view.isFlippedDown();
    }

    private BoardCardView buildView(Photo photo, int row, int column) {
        BoardCardView view = (BoardCardView) inflate(getContext(), R.layout.item_list_card, null);
        int margin = 10;
        GridLayout.LayoutParams params =
                new LayoutParams(GridLayout.spec(row, 1f), GridLayout.spec(column, 1f));
        params.height = 0;
        params.width = 0;
        params.setMargins(margin, margin, margin, margin);
        view.setLayoutParams(params);
        view.setUp(photo);
        view.setOnClickListener(v -> {
            if (!canFlip(view)) {
                return;
            }
            flippedCards.add(view);
            view.flipUp();
            checkMatches();
        });
        return view;
    }

    private void checkMatches() {
        if (flippedCards.size() != 2) {
            return;
        }
        BoardCardView first = flippedCards.get(0);
        BoardCardView second = flippedCards.get(1);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            boolean matches = first.match(second);
            EventBus.getDefault().post(new CardPairSelectionEvent(matches));
            if (matches) {
                onMatch();
                return;
            }
            first.flipDown();
            second.flipDown();
            flippedCards.clear();
        }, 1500);
    }

    private void onMatch() {
        cardsToFlip -= 2;
        flippedCards.get(0).setVisibility(INVISIBLE);
        flippedCards.get(1).setVisibility(INVISIBLE);
        if (cardsToFlip == 0) {
            EventBus.getDefault().post(new CompletionEvent());
        }
        flippedCards.clear();
    }
}
