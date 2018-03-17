package com.games.memorygame.ui.widget


import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet

import com.bumptech.glide.Glide
import com.games.memorygame.model.Photo

import com.bumptech.glide.load.engine.DiskCacheStrategy.ALL
import com.games.memorygame.R
import kotlinx.android.synthetic.main.view_board_card.view.*


class BoardCardView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        CardView(context, attrs, defStyleAttr) {

    private var photo: Photo? = null

    var isFlippedDown = true

    init {
        inflate(context, R.layout.view_board_card, this)
    }

    fun setUp(photo: Photo) {
        this.photo = photo
        Glide.with(context)
                .load(photo.url)
                .diskCacheStrategy(ALL)
                .into(down_picture)
    }

    fun flipUp() {
        this.isFlippedDown = false
        startAnimation(FlipAnimation(up_picture, down_picture))
    }

    fun flipDown() {
        this.isFlippedDown = true
        startAnimation(FlipAnimation(down_picture, up_picture))
    }

    fun match(other: BoardCardView): Boolean = this.photo?.id == other.photo?.id
}
