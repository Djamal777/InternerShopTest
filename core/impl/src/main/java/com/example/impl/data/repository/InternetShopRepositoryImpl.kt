package com.example.impl.data.repository

import com.example.api.data.InternetShopApi
import com.example.api.data.dto.basket.Basket
import com.example.api.data.dto.hot_sales_and_best_sellers.HomeStore
import com.example.api.data.dto.product.Product
import com.example.api.domain.repository.InternetShopRepository

class InternetShopRepositoryImpl(
    private val api: com.example.api.data.InternetShopApi
): com.example.api.domain.repository.InternetShopRepository {
    override suspend fun getHotSalesAndBestSellers(): com.example.api.data.dto.hot_sales_and_best_sellers.HomeStore {
        return api.getHotSalesAnsBestSellers()
    }

    override suspend fun getProductDetails(): com.example.api.data.dto.product.Product {
        return api.getProductDetails()
    }

    override suspend fun getBasket(): com.example.api.data.dto.basket.Basket {
        return api.getBasket()
    }
}