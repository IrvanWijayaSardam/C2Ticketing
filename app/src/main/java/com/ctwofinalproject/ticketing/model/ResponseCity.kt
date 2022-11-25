package com.ctwofinalproject.ticketing.model

import com.google.gson.annotations.SerializedName

data class ResponseCity(

	@field:SerializedName("ResponseCity")
	val responseCity: List<ResponseCityItem?>? = null
)

data class ResponseCityItem(

	@field:SerializedName("province_id")
	val provinceId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
