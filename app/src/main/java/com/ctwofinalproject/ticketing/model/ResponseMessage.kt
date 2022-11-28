package com.ctwofinalproject.ticketing.model

import com.google.gson.annotations.SerializedName

data class ResponseMessage(

	@field:SerializedName("msg")
	val msg: String? = null
)
