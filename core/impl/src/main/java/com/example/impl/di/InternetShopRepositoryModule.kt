package com.example.impl.di

import com.example.api.di.apiModule
import org.koin.dsl.module

val internetShopRepositoryModule= module {
    includes(apiModule)
    single<com.example.api.domain.repository.InternetShopRepository> {
        com.example.impl.data.repository.InternetShopRepositoryImpl(get())
    }
}