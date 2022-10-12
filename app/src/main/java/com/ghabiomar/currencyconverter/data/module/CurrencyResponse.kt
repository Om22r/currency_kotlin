package com.ghabiomar.currencyconverter.data.module

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("base")
    val base: String? = null,
    @SerializedName("date")
    val date: String? = null,
    val rates: Rates,
    @SerializedName("Boolean")
    val success: Boolean? = null
)
