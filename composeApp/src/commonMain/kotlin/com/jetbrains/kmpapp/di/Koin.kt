package com.jetbrains.kmpapp.di

import com.apollographql.apollo.ApolloClient
import com.jetbrains.kmpapp.data.InMemoryMuseumStorage
import com.jetbrains.kmpapp.data.KtorMuseumGraphApi
import com.jetbrains.kmpapp.data.MuseumGraphApi
import com.jetbrains.kmpapp.data.MuseumRepository
import com.jetbrains.kmpapp.data.MuseumStorage
import com.jetbrains.kmpapp.screens.list.ListViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single {
        val serverUrl =
            "http://10.0.2.2:8080/graphql"
        ApolloClient.Builder().serverUrl(serverUrl).build()
    }

    single<MuseumGraphApi> { KtorMuseumGraphApi(get()) }
    single<MuseumStorage> { InMemoryMuseumStorage() }
    single {
        MuseumRepository(get(), get()).apply {
            initialize()
        }
    }
}

val viewModelModule = module {
    factoryOf(::ListViewModel)
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            viewModelModule,
        )
    }
}
