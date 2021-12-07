package com.studywithme.app.business.providers

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.studywithme.app.R

class GlideProvider : IGlideProvider {
    override fun loadImage(imageUri: String, imageView: ImageView) {
        Glide.with(imageView)
            .asBitmap()
            .load(imageUri)
            .centerCrop()
            // .placeholder(context.createProgressPlaceholderDrawable())
            .error(R.drawable.outline_add_a_photo_black_48)
            .fallback(R.drawable.outline_add_a_photo_black_48)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)
            .waitForLayout()
            .clearOnDetach()
    }
}
