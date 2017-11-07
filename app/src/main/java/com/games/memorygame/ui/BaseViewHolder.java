package com.themobilecompany.memorygame.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private final View view;
    private T item;

    public BaseViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        ButterKnife.bind(this, itemView);
    }

    protected Context getContext() {
        return view.getContext();
    }

    public void onBindView(T item) {
        this.item = item;
    }

    protected View getItemView() {
        return view;
    }
}