package com.studywithme.app.modules

import com.studywithme.app.business.providers.GlideProvider
import com.studywithme.app.business.providers.IGlideProvider
import com.studywithme.app.business.providers.INetworkProvider
import com.studywithme.app.business.providers.NetworkProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val baseModule = module() {

    factory<IGlideProvider> { GlideProvider(androidContext()) }

    factory<INetworkProvider> { NetworkProvider(androidContext()) }
}
