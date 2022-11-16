package com.example.cart.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.data.dto.basket.Basket
import com.example.cart.domain.use_cases.GetBasket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(
    private val getBasket: GetBasket
):ViewModel() {

    private val _basket=MutableLiveData<com.example.common.Resource<com.example.api.data.dto.basket.Basket>>()
    val basket:LiveData<com.example.common.Resource<com.example.api.data.dto.basket.Basket>> = _basket

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getBasketInfo()
        }
    }

    private suspend fun getBasketInfo() {
        getBasket().collect{
            _basket.postValue(it)
        }
    }
}