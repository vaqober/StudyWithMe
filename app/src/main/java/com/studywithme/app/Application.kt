package com.studywithme.app

import android.app.Application
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
