package com.example.cart.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cart.databinding.FragmentCartBinding
import com.example.cart.presentation.adapters.basketProductDelegate
import com.example.cart.presentation.viewmodels.CartViewModel
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    private val viewModel by sharedViewModel<CartViewModel>()
    private lateinit var productAdapter: ListDelegationAdapter<List<com.example.api.data.dto.basket.BasketProduct>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.back.setOnClickListener { findNavController().popBackStack() }
        setupRecyclerView()
        observeBasketInfo()
    }

    private fun observeBasketInfo() {
        viewModel.basket.observe(viewLifecycleOwner) {
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
                        it.data?.let { data ->
                            productAdapter.items = data.basket
                            productAdapter.notifyDataSetChanged()
                            total.text = "$${data.total} us"
                            delivery.text = data.delivery
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        productAdapter = ListDelegationAdapter(basketProductDelegate(requireContext()))
        binding.recyclerViewCart.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}