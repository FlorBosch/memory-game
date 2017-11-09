package com.games.memorygame.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

import butterknife.ButterKnife

open class BaseViewHolder<T>(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    private var item: T? = null


    protected val context: Context
        get() = itemView.getContext()

    init {
        ButterKnife.bind(this, this.itemView)
    }

    open fun onBindView(item: T) {
        this.item = item
    }
}