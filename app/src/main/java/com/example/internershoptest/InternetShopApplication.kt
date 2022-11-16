package com.example.internershoptest

import android.app.Application
import android.content.Context
import com.example.api.di.apiModule
import com.example.cart.di.cartModule
import com.example.explorer.di.explorerModule
import com.example.internershoptest.di.appModule
import com.example.product_details.di.productDetailsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class InternetShopApplication :Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@InternetShopApplication)
            modules(listOf(cartModule, explorerModule, productDetailsModule))
        }
    }
}