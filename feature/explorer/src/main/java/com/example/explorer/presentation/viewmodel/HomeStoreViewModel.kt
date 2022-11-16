package com.example.explorer.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeStoreViewModel(
    private val getHotSalesAndBestSellers: com.example.explorer.domain.use_cases.GetHotSalesAndBestSellers
) : ViewModel() {

    private val _bannersAndBestSellers =
        MutableLiveData<com.example.common.Resource<com.example.api.data.dto.hot_sales_and_best_sellers.HomeStore>>()
    val bannersAndBestSellers: LiveData<com.example.common.Resource<com.example.api.data.dto.hot_sales_and_best_sellers.HomeStore>> =
        _bannersAndBestSellers

    val isBottomSheetShown=MutableLiveData<Boolean>(false)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getBannersAndBestSellers()
        }
    }

    private suspend fun getBannersAndBestSellers() {
        getHotSalesAndBestSellers().collect {
            _bannersAndBestSellers.postValue(it)
        }
    }

}