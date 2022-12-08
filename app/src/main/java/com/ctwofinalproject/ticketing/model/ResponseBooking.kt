package com.ctwofinalproject.ticketing.model

import com.google.gson.annotations.SerializedName

data class ResponseBooking(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: DataBooking? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Booking(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("isBooking")
	val isBooking: Boolean? = null,

	@field:SerializedName("ticket_id_departure")
	val ticketIdDeparture: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("ticket_id_return")
	val ticketIdReturn: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class Userbooking(

	@field:SerializedName("booking_id")
	val bookingId: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class DataBooking(

	@field:SerializedName("booking")
	val booking: Booking? = null,

	@field:SerializedName("passangerBulk")
	val passangerBulk: List<PassangerBulkItem?>? = null,

	@field:SerializedName("userbooking")
	val userbooking: Userbooking? = null,

	@field:SerializedName("payment")
	val payment: Payment? = null,

	@field:SerializedName("passangerBooking")
	val passangerBooking: List<PassangerBookingItem?>? = null
)

data class PassangerBookingItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("idBooking")
	val idBooking: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("idPassanger")
	val idPassanger: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class Payment(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("totalPrice")
	val totalPrice: Int? = null,

	@field:SerializedName("userBooking_id")
	val userBookingId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("isPayed")
	val isPayed: Boolean? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class PassangerBulkItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("firstname")
	val firstname: String? = null,

	@field:SerializedName("identityNumber")
	val identityNumber: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("age")
	val age: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
