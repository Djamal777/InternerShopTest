package com.example.explorer.di

import com.example.api.di.apiModule
import com.example.explorer.domain.use_cases.GetHotSalesAndBestSellers
import com.example.explorer.presentation.viewmodel.HomeStoreViewModel
import com.example.impl.di.internetShopRepositoryModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val explorerModule=module{
    includes(internetShopRepositoryModule)
    viewModel{
        HomeStoreViewModel(GetHotSalesAndBestSellers(get()))
    }
}