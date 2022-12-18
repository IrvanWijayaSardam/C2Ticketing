package com.ctwofinalproject.ticketing.model

import com.google.gson.annotations.SerializedName

data class ResponseWhoami(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("currentUser")
	val currentUser: CurrentUser? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class CurrentUser(

	@field:SerializedName("firstname")
	val firstname: String? = null,

	@field:SerializedName("birthdate")
	val birthdate: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("pictures")
	val pictures: Any? = null,

	@field:SerializedName("lastname")
	val lastname: String? = null
)
