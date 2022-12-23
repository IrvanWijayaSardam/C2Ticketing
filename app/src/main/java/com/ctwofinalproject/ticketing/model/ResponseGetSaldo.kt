package com.ctwofinalproject.ticketing.model

import com.google.gson.annotations.SerializedName

data class ResponseGetSaldo(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Data(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("balance")
	val balance: Int? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
