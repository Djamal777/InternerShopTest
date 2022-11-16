package com.example.api.data.dto.basket


import com.google.gson.annotations.SerializedName

data class BasketProduct(
    @SerializedName("id")
    val id: Int,
    @SerializedName("images")
    val images: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("title")
    val title: String
)