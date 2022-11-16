package com.example.explorer.presentation.adapters

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.example.api.databinding.BannerItemBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun bannerAdapterDelegate(c: Context) =
    adapterDelegateViewBinding<com.example.api.data.dto.hot_sales_and_best_sellers.HomeStoreBanner, com.example.api.data.dto.hot_sales_and_best_sellers.HomeStoreBanner, BannerItemBinding>(
        { layoutInflater, root -> BannerItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.apply {
                Glide.with(c)
                    .load(item.picture)
                    .into(bannerImage)
                if (item.isNew) {
                    newBanner.visibility = View.VISIBLE
                }
                if (item.isBuy) {
                    buy.visibility = View.VISIBLE
                }
                if (adapterPosition == 1) {
                    name.visibility = View.INVISIBLE
                }
                name.text = item.title
                description.text = item.subtitle
            }
        }
    }