package com.ctwofinalproject.ticketing.data

import com.google.gson.annotations.SerializedName

data class BalanceBody(

	@field:SerializedName("balance")
	val balance: Int
)
