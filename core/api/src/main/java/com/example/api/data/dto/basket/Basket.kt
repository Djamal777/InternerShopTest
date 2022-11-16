package com.example.api.data.dto.basket


import com.google.gson.annotations.SerializedName

data class Basket(
    @SerializedName("basket")
    val basket: List<BasketProduct>,
    @SerializedName("delivery")
    val delivery: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("total")
    val total: Int
)