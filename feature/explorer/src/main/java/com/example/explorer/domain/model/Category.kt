package com.example.explorer.domain.model

data class Category(
    val id: Int,
    val icon: String,
    val name: String,
    var selected: Boolean = false
)
