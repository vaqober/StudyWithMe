package com.studywithme.app

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.studywithme.app.modules.baseModule
import com.studywithme.app.modules.roomsModule
import com.studywithme.app.modules.usersModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            androidLogger()

            modules(
                baseModule,
                roomsModule,
                usersModule
            )
        }
    }
}

fun Context.createProgressPlaceholderDrawable(): Drawable {
    return CircularProgressDrawable(this).apply {
        strokeWidth = resources.getDimension(R.dimen.element_margin)
        centerRadius = resources.getDimension(R.dimen.text_margin)
        setColorSchemeColors(
            ContextCompat.getColor(
                this@createProgressPlaceholderDrawable,
                R.color.black
            )
        )
        start()
    }
}
