package com.example.cart.di

import com.example.api.di.apiModule
import com.example.cart.domain.use_cases.GetBasket
import com.example.cart.presentation.viewmodels.CartViewModel
import com.example.impl.di.internetShopRepositoryModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cartModule=module{
    includes(internetShopRepositoryModule)
    viewModel{
        CartViewModel(GetBasket(get()))
    }
}