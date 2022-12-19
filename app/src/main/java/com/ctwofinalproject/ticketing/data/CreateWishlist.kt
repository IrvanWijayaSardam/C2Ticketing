package com.ctwofinalproject.ticketing.data

import com.google.gson.annotations.SerializedName

data class CreateWishlist(

	@field:SerializedName("ticket_id_departure")
	val ticketIdDeparture: Int? = null,

	@field:SerializedName("ticket_id_return")
	val ticketIdReturn: Int? = null
)
