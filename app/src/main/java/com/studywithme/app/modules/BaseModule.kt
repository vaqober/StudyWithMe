package com.studywithme.app.modules

import com.studywithme.app.business.providers.GlideProvider
import com.studywithme.app.business.providers.IGlideProvider
import org.koin.core.scope.get
import org.koin.dsl.module

val baseModule = module() {
    factory<IGlideProvider> { GlideProvider() }
}