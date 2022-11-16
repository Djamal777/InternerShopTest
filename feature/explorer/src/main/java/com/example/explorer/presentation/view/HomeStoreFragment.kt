package com.example.explorer.presentation.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.api.di.apiModule
import com.example.explorer.R
import com.example.explorer.data.initial.getCategories
import com.example.explorer.databinding.FragmentHomeStoreBinding
import com.example.explorer.di.explorerModule
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class HomeStoreFragment : Fragment() {

    private lateinit var binding: FragmentHomeStoreBinding
    private val viewModel by sharedViewModel<com.example.explorer.presentation.viewmodel.HomeStoreViewModel>()
    private lateinit var categoryAdapter: ListDelegationAdapter<List<com.example.explorer.domain.model.Category>>
    private lateinit var bannerAdapter: ListDelegationAdapter<List<com.example.api.data.dto.hot_sales_and_best_sellers.HomeStoreBanner>>
    private lateinit var productAdapter: ListDelegationAdapter<List<com.example.api.data.dto.hot_sales_and_best_sellers.BestSeller>>
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeStoreBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomFilters)
        bottomSheetBehavior.isHideable = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        binding.apply {
            toolbar.menu.findItem(R.id.filter).isVisible = false
            toolbar.menu.findItem(R.id.filter).setOnMenuItemClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                viewModel.isBottomSheetShown.value=true
                true
            }
            cancel.setOnClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                viewModel.isBottomSheetShown.value=false
            }
            done.setOnClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                viewModel.isBottomSheetShown.value=false
            }
            geoPosition.compoundDrawablesRelative[0].setTint(resources.getColor(com.example.api.R.color.secondaryColor))
        }
        setupRecyclerViews()
        observeBannersAndBestSellers()
    }

    private fun observeBannersAndBestSellers() {
        viewModel.bannersAndBestSellers.observe(viewLifecycleOwner) {
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
                        val brands = ArrayAdapter.createFromResource(
                            requireContext(),
                            R.array.brands,
                            android.R.layout.simple_spinner_dropdown_item
                        )
                        val prices = ArrayAdapter.createFromResource(
                            requireContext(),
                            R.array.prices,
                            android.R.layout.simple_spinner_dropdown_item
                        )
                        val sizes = ArrayAdapter.createFromResource(
                            requireContext(),
                            R.array.sizes,
                            android.R.layout.simple_spinner_dropdown_item
                        )
                        priceSpinner.adapter = prices
                        brandSpinner.adapter = brands
                        sizeSpinner.adapter = sizes
                        priceSpinner.setSelection(0)
                        brandSpinner.setSelection(0)
                        sizeSpinner.setSelection(0)
                        progressBar.visibility = View.INVISIBLE
                        content.visibility = View.VISIBLE
                        toolbar.menu.findItem(R.id.filter).isVisible = true
                    }
                    bannerAdapter.items = it.data?.homeStore
                    bannerAdapter.notifyDataSetChanged()
                    productAdapter.items = it.data?.bestSeller
                    productAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun setupRecyclerViews() {
        binding.apply {
            categoryAdapter =
                ListDelegationAdapter(
                    com.example.explorer.presentation.adapters.categoryDelegate(
                        requireContext(),
                        categoryClickListener
                    )
                )
            categoryAdapter.items= getCategories(requireContext())
            categoryAdapter.notifyDataSetChanged()
            recyclerViewCategories.adapter = categoryAdapter
            recyclerViewCategories.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            bannerAdapter = ListDelegationAdapter(
                com.example.explorer.presentation.adapters.bannerAdapterDelegate(
                    requireContext()
                )
            )
            recyclerViewHotSales.adapter = bannerAdapter
            recyclerViewHotSales.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            productAdapter =
                ListDelegationAdapter(
                    com.example.explorer.presentation.adapters.productDelegate(
                        requireContext(),
                        productClickListener
                    )
                )
            recyclerViewBestSellers.adapter = productAdapter
            recyclerViewBestSellers.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private val productClickListener: (com.example.api.data.dto.hot_sales_and_best_sellers.BestSeller) -> Unit = {
            val uri= Uri.parse("myApp://productDetailsFragment")
            findNavController().navigate(uri)
        }

    private val categoryClickListener: (Int) -> Unit = { position ->
        categoryAdapter.apply {
            var lastSelected = -1
            items?.let { items ->
                if (items[position].selected) {
                    items[position].selected = false
                    notifyItemChanged(position)
                } else {
                    lastSelected =
                        items.indexOf(items.find { it.selected }?.apply {
                            selected = false
                        })
                    items[position].selected = true
                }
                notifyItemChanged(position)
                if (lastSelected != -1) notifyItemChanged(lastSelected)
            }
        }
    }
}