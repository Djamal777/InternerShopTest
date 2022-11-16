package com.example.explorer.data.initial

import android.content.Context
import com.example.explorer.R

fun getCategories(c: Context) = listOf<com.example.explorer.domain.model.Category>(
    com.example.explorer.domain.model.Category(
        1,
        c.resources.getResourceName(R.drawable.outline_smartphone_24),
        "Phones"
    ),
    com.example.explorer.domain.model.Category(
        2,
        c.resources.getResourceName(R.drawable.outline_computer_24),
        "Computer"
    ),
    com.example.explorer.domain.model.Category(
        3,
        c.resources.getResourceName(R.drawable.heart_pulse),
        "Health"
    ),
    com.example.explorer.domain.model.Category(
        4,
        c.resources.getResourceName(R.drawable.bookshelf),
        "Books"
    ),
    com.example.explorer.domain.model.Category(
        5,
        c.resources.getResourceName(R.drawable.outline_checkroom_24),
        "Clothes"
    ),
)
