package com.games.memorygame.ui.widget;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.games.memorygame.R;
import com.games.memorygame.model.Photo;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.ALL;

public class BoardCardView extends CardView {

    @BindView(R.id.up_picture)
    ImageView upPicture;

    @BindView(R.id.down_picture)
    ImageView downPicture;

    private Photo photo;

    private boolean flippedDown = true;

    public BoardCardView(Context context) {
        super(context);
    }

    public BoardCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BoardCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    void setUp(Photo photo) {
        this.photo = photo;
        Glide.with(getContext())
                .load(photo.getUrl())
                .diskCacheStrategy(ALL)
                .into(downPicture);
    }
    //CountDownLatch

    boolean isFlippedDown() {
        return flippedDown;
    }

    void flipUp() {
        this.flippedDown = false;
        startAnimation(new FlipAnimation(upPicture, downPicture));
    }

    void flipDown() {
        this.flippedDown = true;
        startAnimation(new FlipAnimation(downPicture, upPicture));
    }

    boolean match(BoardCardView other) {
        return this.photo.getId().equals(other.photo.getId());
    }
}
