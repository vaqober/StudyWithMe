package com.studywithme.app.modules

import com.google.gson.GsonBuilder
import com.studywithme.app.business.providers.IUserProvider
import com.studywithme.app.business.providers.UserProvider
import com.studywithme.app.datalayer.accessors.IUserAccessor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val usersModule = module() {
    factory<IUserProvider> { UserProvider(get()) }

    factory<IUserAccessor> {
//        val baseUrl = "https://6161de9737492500176314c6.mockapi.io/api/develop/v1/"
        val baseUrl = "https://studywithme-e5a96-default-rtdb.europe-west1.firebasedatabase.app/"

        val gson = GsonBuilder()
            .create()

        val gsonConverter = GsonConverterFactory.create(gson)

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverter)
            .build()
            .create(IUserAccessor::class.java)
    }
}
