package com.studywithme.app.business.providers

import android.widget.ImageView
import androidx.fragment.app.Fragment

interface IGlideProvider {
    fun loadImage(imageUri: String, imageView: ImageView, fragment: Fragment)
}