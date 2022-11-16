package com.example.api.domain.repository

import com.example.api.data.dto.basket.Basket
import com.example.api.data.dto.hot_sales_and_best_sellers.HomeStore
import com.example.api.data.dto.product.Product

interface InternetShopRepository {

    suspend fun getHotSalesAndBestSellers(): com.example.api.data.dto.hot_sales_and_best_sellers.HomeStore

    suspend fun getProductDetails(): com.example.api.data.dto.product.Product

    suspend fun getBasket(): com.example.api.data.dto.basket.Basket
}