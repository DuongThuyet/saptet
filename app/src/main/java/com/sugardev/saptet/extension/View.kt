package com.sugardev.saptet.extension

import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation

fun View.showSlideInDown(duration: Long, onAnimationEnd: (() -> Unit)? = null) {
    val anim = TranslateAnimation(0f, 0f, 0f, this.height.toFloat())
    val view = this
    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {
        }

        override fun onAnimationEnd(animation: Animation?) {
            view.gone()
            onAnimationEnd?.invoke()
        }

        override fun onAnimationStart(animation: Animation?) {
        }
    })
    anim.duration = duration
    this.startAnimation(anim)
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}