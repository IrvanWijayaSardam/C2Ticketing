package com.ctwofinalproject.ticketing.data

import com.google.gson.annotations.SerializedName

data class ResponseProvince(

    @field:SerializedName("ResponseProvince")
    val responseProvince: List<ResponseProvinceItem?>? = null
)

data class ResponseProvinceItem(

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("id")
    var id: String? = null
)

