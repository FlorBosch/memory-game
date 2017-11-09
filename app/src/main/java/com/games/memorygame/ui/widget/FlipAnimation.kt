package com.games.memorygame.ui.widget

import android.graphics.Camera
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation


class FlipAnimation(private val fromView: View, private val toView: View) : Animation() {
    private var camera: Camera? = null

    private var centerX: Float = 0.toFloat()
    private var centerY: Float = 0.toFloat()

    init {
        duration = 700
        fillAfter = false
        interpolator = AccelerateDecelerateInterpolator()
    }

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
        centerX = (width / 2).toFloat()
        centerY = (height / 2).toFloat()
        camera = Camera()
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val radians = Math.PI * interpolatedTime
        var degrees = (180.0 * radians / Math.PI).toFloat()

        if (interpolatedTime >= 0.5f) {
            degrees -= 180f
            fromView.visibility = View.GONE
            toView.visibility = View.VISIBLE
        }
        degrees = -degrees
        val matrix = t.matrix
        camera!!.save()
        camera!!.rotateY(degrees)
        camera!!.getMatrix(matrix)
        camera!!.restore()
        matrix.preTranslate(-centerX, -centerY)
        matrix.postTranslate(centerX, centerY)
    }
}
