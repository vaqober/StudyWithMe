package com.studywithme.app.business.providers

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.studywithme.app.R

class GlideProvider : IGlideProvider {
    override fun loadImage(imageUri: String, imageView: ImageView, isCircle: Boolean) {
        var glide = Glide.with(imageView).asBitmap()
        glide = if (isCircle) {
            glide.circleCrop()
        } else {
            glide.centerCrop()
        }
        glide
            .load(imageUri)
            .placeholder(R.drawable.outline_add_a_photo_black_48)
            .error(R.drawable.outline_add_a_photo_black_48)
            .fallback(R.drawable.outline_add_a_photo_black_48)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView)
            .waitForLayout()
    }
}
