package com.example.internershoptest.di

import com.example.product_details.domain.use_cases.GetProductDetails
import com.example.product_details.presentation.viewmodel.ProductDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.example.api.data.InternetShopApi::class.java)
    }
    single<com.example.api.domain.repository.InternetShopRepository> {
        com.example.impl.data.repository.InternetShopRepositoryImpl(get())
    }
    viewModel {
        com.example.explorer.presentation.viewmodel.HomeStoreViewModel(
            com.example.explorer.domain.use_cases.GetHotSalesAndBestSellers(
                get()
            )
        )
    }
    viewModel {
        ProductDetailsViewModel(GetProductDetails(get()))
    }
    viewModel {
        com.example.cart.presentation.viewmodels.CartViewModel(
            com.example.cart.domain.use_cases.GetBasket(
                get()
            )
        )
    }

}