package com.example.explorer.presentation.adapters

import android.content.Context
import android.graphics.Paint
import com.bumptech.glide.Glide
import com.example.api.databinding.BestSellerItemBinding
import com.example.explorer.R
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import java.text.NumberFormat
import java.util.*

fun productDelegate(
    c: Context,
    itemClickListener: (com.example.api.data.dto.hot_sales_and_best_sellers.BestSeller) -> Unit
) =
    adapterDelegateViewBinding<com.example.api.data.dto.hot_sales_and_best_sellers.BestSeller, com.example.api.data.dto.hot_sales_and_best_sellers.BestSeller, BestSellerItemBinding>(
        { layoutInflater, root -> BestSellerItemBinding.inflate(layoutInflater, root, false) }
    ) {
        binding.apply {
            root.setOnClickListener { itemClickListener(item) }
            favouriteIcon.setOnClickListener {
                if (item.isFavorites) {
                    favouriteIcon.setIconResource(R.drawable.outline_favorite_border_24)
                } else favouriteIcon.setIconResource(com.example.api.R.drawable.outline_favorite_24)
                item.isFavorites = !item.isFavorites
            }
        }
        bind {
            binding.apply {
                Glide.with(c)
                    .load(item.picture)
                    .into(productImage)
                previousPrice.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    text = c.resources.getString(
                        R.string.price_dollar,
                        NumberFormat.getNumberInstance(Locale.US).format(item.priceWithoutDiscount)
                    )
                }
                price.text = c.resources.getString(
                    R.string.price_dollar,
                    NumberFormat.getNumberInstance(Locale.US).format(item.discountPrice)
                )
                productName.text = item.title
                if (item.isFavorites) {
                    favouriteIcon.setIconResource(com.example.api.R.drawable.outline_favorite_24)
                } else favouriteIcon.setIconResource(R.drawable.outline_favorite_border_24)
            }
        }
    }