package com.ctwofinalproject.ticketing.data

import com.google.gson.annotations.SerializedName

data class UpdatePassword(

	@field:SerializedName("oldPassword")
	val oldPassword: String? = null,

	@field:SerializedName("newPassword")
	val newPassword: String? = null,

	@field:SerializedName("confPassword")
	val confPassword: String? = null
)
