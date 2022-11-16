package com.example.product_details.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.product_details.databinding.FragmentProductCharacteristicsBinding
import com.example.product_details.presentation.viewmodel.ProductDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProductCharacteristicsFragment : Fragment() {

    private lateinit var binding: FragmentProductCharacteristicsBinding
    private val viewModel by sharedViewModel<ProductDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductCharacteristicsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeProductCharacteristics()
    }

    private fun observeProductCharacteristics() {
        viewModel.productDetails.observe(viewLifecycleOwner) {
            when (it) {
                is com.example.common.Resource.Success -> {
                    binding.apply {
                        it.data?.let {data->
                            cpu.text = data.cPU
                            memory.text=data.sd
                            ram.text=data.ssd
                            camera.text=data.camera
                        }
                    }
                }
                else -> Unit
            }
        }
    }

}