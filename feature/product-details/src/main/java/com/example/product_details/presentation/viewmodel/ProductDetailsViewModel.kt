package com.example.product_details.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.data.dto.product.Product
import com.example.product_details.domain.use_cases.GetProductDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val getProductDetails: com.example.product_details.domain.use_cases.GetProductDetails
):ViewModel() {

    private val _productDetails=MutableLiveData<com.example.common.Resource<com.example.api.data.dto.product.Product>>()
    val productDetails:LiveData<com.example.common.Resource<com.example.api.data.dto.product.Product>> = _productDetails

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getDetails()
        }
    }

    private suspend fun getDetails() {
        getProductDetails().collect{
            _productDetails.postValue(it)
        }
    }
}