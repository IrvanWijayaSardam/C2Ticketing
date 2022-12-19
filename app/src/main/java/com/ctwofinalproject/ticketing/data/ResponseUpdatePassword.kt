package com.ctwofinalproject.ticketing.data

import com.google.gson.annotations.SerializedName

data class ResponseUpdatePassword(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
