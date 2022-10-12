package com.ghabiomar.currencyconverter.Main

import com.ghabiomar.currencyconverter.data.module.CurrencyResponse
import com.ghabiomar.currencyconverter.util.Resources

interface MainRepository {
    suspend fun getRates(base :String): Resources <CurrencyResponse>
}