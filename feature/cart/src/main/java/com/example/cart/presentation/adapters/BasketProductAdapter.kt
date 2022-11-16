package com.example.cart.presentation.adapters

import android.content.Context
import com.bumptech.glide.Glide
import com.example.api.databinding.CartItemBinding
import com.example.cart.R
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import java.text.NumberFormat
import java.util.*

fun basketProductDelegate(c: Context) =
    adapterDelegateViewBinding<com.example.api.data.dto.basket.BasketProduct, com.example.api.data.dto.basket.BasketProduct, CartItemBinding>(
        { layoutInflater, root -> CartItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.apply {
                Glide.with(c)
                    .load(item.images)
                    .into(productImage)
                productName.text = item.title
                productPrice.text = c.resources.getString(R.string.price_dollar, NumberFormat.getNumberInstance(
                    Locale.US).format(item.price))
            }
        }
    }
