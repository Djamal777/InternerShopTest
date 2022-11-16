package com.example.explorer.presentation.adapters

import android.content.Context
import android.graphics.Color
import androidx.appcompat.content.res.AppCompatResources
import com.example.api.databinding.CategoryItemBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun categoryDelegate(c: Context, itemClickListener: (Int) -> Unit) =
    adapterDelegateViewBinding<com.example.explorer.domain.model.Category, com.example.explorer.domain.model.Category, CategoryItemBinding>(
        { layoutInflater, root -> CategoryItemBinding.inflate(layoutInflater, root, false) }
    ) {
        binding.root.setOnClickListener { itemClickListener(adapterPosition) }
        bind {
            binding.apply {
                categoryName.text = item.name
                categoryImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        c,
                        c.resources.getIdentifier(item.icon, "drawable", c.packageName)
                    )
                )
                if (item.selected) {
                    circleCard.setCardBackgroundColor(c.resources.getColor(com.example.api.R.color.secondaryColor))
                    categoryImage.setColorFilter(Color.WHITE)
                } else {
                    circleCard.setCardBackgroundColor(Color.WHITE)
                    categoryImage.setColorFilter(Color.GRAY)
                }
            }
        }
    }