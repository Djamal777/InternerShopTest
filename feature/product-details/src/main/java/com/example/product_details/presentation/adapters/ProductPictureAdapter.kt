package com.example.product_details.presentation.adapters

import android.content.Context
import com.bumptech.glide.Glide
import com.example.api.databinding.ProductPhotoItemBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun productPictureDelegate(c: Context) =
    adapterDelegateViewBinding<String, String, ProductPhotoItemBinding>(
        { layoutInflater, root -> ProductPhotoItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            Glide.with(c)
                .load(item)
                .into(binding.productPicture)
        }
    }
