package com.ghabiomar.currencyconverter.Main

import com.ghabiomar.currencyconverter.data.module.CurrencyApi
import com.ghabiomar.currencyconverter.data.module.CurrencyResponse
import com.ghabiomar.currencyconverter.util.Resources
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
  private val api: CurrencyApi
) :MainRepository {
    override suspend fun getRates(base: String): Resources<CurrencyResponse> {
      return try {
          val response = api.getRates(base)
          val result = response.body()
          if (response.isSuccessful && result != null){
              Resources.Success(result)
          }else{
              Resources.Error(response.message())
          }

      }catch (e : Exception){
          Resources.Error(e.message ?: "An a error occured")
      }
    }
}