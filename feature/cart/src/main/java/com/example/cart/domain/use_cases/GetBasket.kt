package com.example.cart.domain.use_cases

import com.bumptech.glide.load.HttpException
import com.example.api.data.dto.basket.Basket
import com.example.api.domain.repository.InternetShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetBasket(
    private val repository: com.example.api.domain.repository.InternetShopRepository
) {
    operator fun invoke(): Flow<com.example.common.Resource<com.example.api.data.dto.basket.Basket>> = flow{
        try{
            emit(com.example.common.Resource.Loading())
            val basket=repository.getBasket()
            emit(com.example.common.Resource.Success(basket))
        }catch (e: HttpException) {
            emit(com.example.common.Resource.Error("Неизвестная ошибка"))
        }catch (e: IOException) {
            emit(com.example.common.Resource.Error("Нет интернет-соединения"))
        }
    }
}