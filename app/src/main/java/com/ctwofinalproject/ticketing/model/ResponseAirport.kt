package com.ctwofinalproject.ticketing.model

import com.google.gson.annotations.SerializedName

data class ResponseAirport(

	@field:SerializedName("ResponseAirport")
	val responseAirport: List<ResponseAirportItem?>? = null
)

data class ResponseAirportItem(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("terminal")
	val terminal: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
