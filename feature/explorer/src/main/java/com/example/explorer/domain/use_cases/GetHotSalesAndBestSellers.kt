package com.example.explorer.domain.use_cases

import com.bumptech.glide.load.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetHotSalesAndBestSellers(
    private val repository: com.example.api.domain.repository.InternetShopRepository
) {
    operator fun invoke(): Flow<com.example.common.Resource<com.example.api.data.dto.hot_sales_and_best_sellers.HomeStore>> =
        flow {
            try {
                emit(com.example.common.Resource.Loading())
                val homeStore = repository.getHotSalesAndBestSellers()
                emit(com.example.common.Resource.Success(homeStore))
            } catch (e: HttpException) {
                emit(com.example.common.Resource.Error("Неизвестная ошибка"))
            } catch (e: IOException) {
                emit(com.example.common.Resource.Error("Нет интернет-соединения"))
            }
        }
}