package com.studywithme.app.business.providers

import android.widget.ImageView

interface IGlideProvider {
    fun loadImage(imageUri: String, imageView: ImageView, isCircle: Boolean)
}
