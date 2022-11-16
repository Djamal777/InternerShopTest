package com.example.api.data.dto.hot_sales_and_best_sellers


import com.google.gson.annotations.SerializedName

data class HomeStore(
    @SerializedName("best_seller")
    val bestSeller: List<BestSeller>,
    @SerializedName("home_store")
    val homeStore: List<HomeStoreBanner>
)