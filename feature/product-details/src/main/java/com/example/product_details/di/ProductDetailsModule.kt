package com.example.product_details.di

import com.example.api.di.apiModule
import com.example.impl.di.internetShopRepositoryModule
import com.example.product_details.domain.use_cases.GetProductDetails
import com.example.product_details.presentation.viewmodel.ProductDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val productDetailsModule= module {
    includes(internetShopRepositoryModule)
    viewModel{
        ProductDetailsViewModel(GetProductDetails(get()))
    }
}