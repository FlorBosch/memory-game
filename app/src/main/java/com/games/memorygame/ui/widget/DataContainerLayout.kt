package com.games.memorygame.ui.widget


import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout

import com.games.memorygame.R
import com.games.memorygame.util.ViewUtil

class DataContainerLayout : RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    fun setUp(onNetworkError: () -> Unit) {
        addNetworkErrorView(onNetworkError)
        addLoadingView()
        setVisibility(false, true)
    }

    fun stopLoading() {
        setVisibility(false, false)
    }

    fun displayError() {
        setVisibility(true, false)
    }

    private fun addNetworkErrorView(onNetworkError: () -> Unit) {
        val view = View.inflate(context, R.layout.network_error_layout, null)
        val button = view.findViewById<Button>(R.id.retry_button)
        button.setOnClickListener { _ ->
            onNetworkError()
            setVisibility(false, true)
        }
        addCenteredView(view)
    }

    private fun addLoadingView() {
        addCenteredView(ProgressBar(context))
    }

    private fun setVisibility(networkError: Boolean, inProgress: Boolean) {
        (0 until childCount).forEach {
            val child = getChildAt(it)
            when {
                child.id == R.id.network_error_container -> ViewUtil.setVisibility(networkError, child)
                child is ProgressBar -> ViewUtil.setVisibility(inProgress, child)
                else -> ViewUtil.setVisibility(!networkError && !inProgress, child)
            }
        }
    }

    private fun addCenteredView(view: View) {
        val params = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        addView(view, params)
    }

}
