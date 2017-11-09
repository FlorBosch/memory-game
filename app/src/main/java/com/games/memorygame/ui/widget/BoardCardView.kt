package com.games.memorygame.ui.widget


import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.games.memorygame.R
import com.games.memorygame.model.Photo

import butterknife.BindView
import butterknife.ButterKnife

import com.bumptech.glide.load.engine.DiskCacheStrategy.ALL

class BoardCardView : CardView {

    @BindView(R.id.up_picture) lateinit var upPicture: ImageView

    @BindView(R.id.down_picture) lateinit var downPicture: ImageView

    private var photo: Photo? = null

    //CountDownLatch

    internal var isFlippedDown = true
        private set

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onFinishInflate() {
        super.onFinishInflate()
        ButterKnife.bind(this)
    }

    internal fun setUp(photo: Photo) {
        this.photo = photo
        Glide.with(context)
                .load(photo.url)
                .diskCacheStrategy(ALL)
                .into(downPicture)
    }

    internal fun flipUp() {
        this.isFlippedDown = false
        startAnimation(FlipAnimation(upPicture, downPicture))
    }

    internal fun flipDown() {
        this.isFlippedDown = true
        startAnimation(FlipAnimation(downPicture, upPicture))
    }

    internal fun match(other: BoardCardView): Boolean {
        return this.photo!!.id == other.photo!!.id
    }
}
