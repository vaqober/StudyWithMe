package com.studywithme.app.business.providers

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.studywithme.app.R

class GlideProvider(private val context: Context) : IGlideProvider {
    override fun loadImage(imageUri: String, imageView: ImageView, isCircle: Boolean) {
        var glide = Glide.with(imageView).asBitmap()
        glide = if (isCircle) {
            glide.circleCrop()
        } else {
            glide.centerCrop()
        }
        glide
                .load(imageUri)
                .placeholder(context.createProgressPlaceholderDrawable())
                .error(R.drawable.ic_user_svg)
                .fallback(R.drawable.ic_user_svg)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView)
                .waitForLayout()
    }
}

private fun Context.createProgressPlaceholderDrawable(): Drawable {
    return CircularProgressDrawable(this).apply {
        strokeWidth = resources.getDimension(R.dimen.progress_width)
        centerRadius = resources.getDimension(R.dimen.progress_radius)
        setColorSchemeColors(
                ContextCompat.getColor(
                        this@createProgressPlaceholderDrawable,
                        R.color.black
                )
        )
        start()
    }
}
