package com.example.api.data

import com.example.api.data.dto.basket.Basket
import com.example.api.data.dto.hot_sales_and_best_sellers.HomeStore
import com.example.api.data.dto.product.Product
import retrofit2.http.GET

interface InternetShopApi {

    @GET("/v3/654bd15e-b121-49ba-a588-960956b15175")
    suspend fun getHotSalesAnsBestSellers(): HomeStore

    @GET("/v3/6c14c560-15c6-4248-b9d2-b4508df7d4f5")
    suspend fun getProductDetails(): Product

    @GET("/v3/53539a72-3c5f-4f30-bbb1-6ca10d42c149")
    suspend fun getBasket(): Basket

}