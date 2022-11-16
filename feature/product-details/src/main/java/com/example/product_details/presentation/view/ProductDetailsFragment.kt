package com.example.product_details.presentation.view

import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.product_details.R
import com.example.product_details.databinding.FragmentProductDetailsBinding
import com.example.product_details.presentation.adapters.MyFragmentAdapter
import com.example.product_details.presentation.adapters.productPictureDelegate
import com.example.product_details.presentation.viewmodel.ProductDetailsViewModel
import com.google.android.material.tabs.TabLayout
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.NumberFormat
import java.util.*


class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding
    private val viewModel by sharedViewModel<ProductDetailsViewModel>()
    private lateinit var productPictureCarouselAdapter: ListDelegationAdapter<List<String>>
    private lateinit var myFragmentAdapter: MyFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.back.setOnClickListener { findNavController().popBackStack() }
        setupRecyclerViews()
        observeProductDetails()
    }

    private fun observeProductDetails() {
        viewModel.productDetails.observe(viewLifecycleOwner) {
            when (it) {
                is com.example.common.Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT)
                        .show()
                }
                is com.example.common.Resource.Loading -> {
                    binding.apply {
                        progressBar.visibility = View.VISIBLE
                    }
                }
                is com.example.common.Resource.Success -> {
                    binding.apply {
                        progressBar.visibility = View.INVISIBLE
                        content.visibility = View.VISIBLE
                        recyclerViewProductPhotos.visibility = View.VISIBLE
                        basket.visibility = View.VISIBLE
                        it.data?.let { data ->
                            productPictureCarouselAdapter.items = data.images
                            productPictureCarouselAdapter.notifyDataSetChanged()
                            productName.text = data.title
                            ratingBar.rating = data.rating.toFloat()
                            val layerDrawable = AppCompatResources
                                .getDrawable(
                                    requireContext(),
                                    R.drawable.radio_color_selected
                                ) as LayerDrawable
                            val gradientDrawable = layerDrawable
                                .findDrawableByLayerId(R.id.radio_shape_color) as GradientDrawable
                            gradientDrawable.setColor(data.color[0].toColorInt())
                            color1.background = layerDrawable
                            color2.background.setColorFilter(
                                Color.parseColor(data.color[1]),
                                PorterDuff.Mode.SRC_IN
                            )
                            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                                val layerDrawableInner = AppCompatResources
                                    .getDrawable(
                                        requireContext(),
                                        R.drawable.radio_color_selected
                                    ) as LayerDrawable
                                val gradientDrawableInner = layerDrawableInner
                                    .findDrawableByLayerId(R.id.radio_shape_color) as GradientDrawable
                                if (checkedId == R.id.color1) {
                                    gradientDrawableInner.setColor(data.color[0].toColorInt())
                                    color1.background = layerDrawableInner
                                    color2.background.setColorFilter(
                                        Color.parseColor(data.color[1]),
                                        PorterDuff.Mode.SRC_IN
                                    )
                                } else if (checkedId == R.id.color2) {
                                    gradientDrawableInner.setColor(data.color[1].toColorInt())
                                    binding.color2.background = layerDrawableInner
                                    color1.background.setColorFilter(
                                        Color.parseColor(data.color[0]),
                                        PorterDuff.Mode.SRC_IN
                                    )
                                }
                            }
                            capacity1.text = resources.getString(R.string.gb, data.capacity[0])
                            capacity2.text = resources.getString(R.string.gb, data.capacity[1])
                            addToCart.text = resources.getString(
                                R.string.add_to_cart,
                                NumberFormat.getNumberInstance(Locale.US).format(data.price)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerViews() {
        productPictureCarouselAdapter =
            ListDelegationAdapter(productPictureDelegate(requireContext()))
        binding.recyclerViewProductPhotos.apply {
            adapter = productPictureCarouselAdapter
            set3DItem(false)
            setAlpha(true)
            setFlat(false)
            setIsScrollingEnabled(true)
            setIntervalRatio(0.5f)
        }
        myFragmentAdapter = MyFragmentAdapter(parentFragmentManager, lifecycle)
        binding.viewPager.adapter = myFragmentAdapter
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabLayout = (binding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(
                    tab!!.position
                ) as LinearLayout
                val tabTextView = tabLayout.getChildAt(1) as TextView
                tabTextView.setTypeface(tabTextView.typeface, Typeface.BOLD)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabLayout = (binding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(
                    tab!!.position
                ) as LinearLayout
                val tabTextView = tabLayout.getChildAt(1) as TextView
                tabTextView.setTypeface(
                    ResourcesCompat.getFont(requireContext(), R.font.mark_pro),
                    Typeface.NORMAL
                )
            }

            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
    }
}