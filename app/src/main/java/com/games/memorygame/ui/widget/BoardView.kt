package com.games.memorygame.ui.widget

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.support.v7.widget.GridLayout
import android.view.View

import com.games.memorygame.R
import com.games.memorygame.model.Photo
import com.games.memorygame.ui.event.CompletionEvent
import com.games.memorygame.ui.event.CardPairSelectionEvent

import org.greenrobot.eventbus.EventBus

import java.util.ArrayList


class BoardView : GridLayout {

    private var flippedCards: MutableList<BoardCardView> = ArrayList()
    private var cardsToFlip = 0

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?) : super(context)


    fun setUp(photos: List<Photo>, rows: Int, columns: Int) {
        cardsToFlip = photos.size
        orientation = GridLayout.HORIZONTAL
        columnCount = columns
        rowCount = rows
        (0..rows).forEach { r ->
            (0..columns).forEach { c -> addView(buildView(photos[r * (rows - 1) + c], r, c)) }
        }
    }

    private fun canFlip(view: BoardCardView): Boolean {
        return flippedCards.size < 2 && view.isFlippedDown
    }

    private fun buildView(photo: Photo, row: Int, column: Int): BoardCardView {
        val view = View.inflate(context, R.layout.item_list_card, null) as BoardCardView
        val margin = 10
        val params = GridLayout.LayoutParams(GridLayout.spec(row, 1f), GridLayout.spec(column, 1f))
        params.height = 0
        params.width = 0
        params.setMargins(margin, margin, margin, margin)
        view.layoutParams = params
        view.setUp(photo)
        view.setOnClickListener { _ ->
            if (!canFlip(view)) {
                return@setOnClickListener
            }
            flippedCards.add(view)
            view.flipUp()
            checkMatches()
        }
        return view
    }

    private fun checkMatches() {
        if (flippedCards.size != 2) {
            return
        }
        val first = flippedCards[0]
        val second = flippedCards[1]
        val handler = Handler()
        handler.postDelayed({
            val matches = first.match(second)
            EventBus.getDefault().post(CardPairSelectionEvent(matches))
            if (matches) {
                onMatch()
                return@postDelayed
            }
            first.flipDown()
            second.flipDown()
            flippedCards.clear()
        }, 1500)
    }

    private fun onMatch() {
        cardsToFlip -= 2
        flippedCards[0].visibility = View.INVISIBLE
        flippedCards[1].visibility = View.INVISIBLE
        if (cardsToFlip == 0) {
            EventBus.getDefault().post(CompletionEvent())
        }
        flippedCards.clear()
    }
}
