package com.ctwofinalproject.ticketing.model

import com.google.gson.annotations.SerializedName

data class ResponseUpdate(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
