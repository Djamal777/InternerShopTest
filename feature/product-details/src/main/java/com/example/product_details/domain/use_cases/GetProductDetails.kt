package com.example.product_details.domain.use_cases

import com.bumptech.glide.load.HttpException
import com.example.api.data.dto.product.Product
import com.example.api.domain.repository.InternetShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetProductDetails(
    private val repository: com.example.api.domain.repository.InternetShopRepository
) {
    operator fun invoke(): Flow<com.example.common.Resource<com.example.api.data.dto.product.Product>> = flow{
        try{
            emit(com.example.common.Resource.Loading())
            val productDetails=repository.getProductDetails()
            emit(com.example.common.Resource.Success(productDetails))
        }catch (e: HttpException) {
            emit(com.example.common.Resource.Error("Неизвестная ошибка"))
        }catch (e: IOException) {
            emit(com.example.common.Resource.Error("Нет интернет-соединения"))
        }
    }
}