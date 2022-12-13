package com.ctwofinalproject.ticketing.model

import com.google.gson.annotations.SerializedName

data class ResponseGetWishlist(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItemWishlist?>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class TicketDeparture(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("flight")
	val flight: FlightInfoWishlist? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("class_id")
	val classId: Int? = null,

	@field:SerializedName("isRoundTrip")
	val isRoundTrip: Boolean? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("flight_id")
	val flightId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class FlightInfoWishlist(

	@field:SerializedName("departureTime")
	val departureTime: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("departureAirport")
	val departureAirport: Int? = null,

	@field:SerializedName("arrivalTime")
	val arrivalTime: String? = null,

	@field:SerializedName("DepartureTerminal")
	val departureTerminal: DepartureTerminalWishlist? = null,

	@field:SerializedName("planeId")
	val planeId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("departureDate")
	val departureDate: String? = null,

	@field:SerializedName("ArrivalTerminal")
	val arrivalTerminal: ArrivalTerminalWishlist? = null,

	@field:SerializedName("arrivalAirport")
	val arrivalAirport: Int? = null,

	@field:SerializedName("arrivalDate")
	val arrivalDate: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class DepartureTerminalWishlist(

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

data class Users(

	@field:SerializedName("firstname")
	val firstname: String? = null,

	@field:SerializedName("birthdate")
	val birthdate: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("pictures")
	val pictures: String? = null,

	@field:SerializedName("lastname")
	val lastname: String? = null,

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("refresh_token")
	val refreshToken: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("isfacebookauth")
	val isfacebookauth: Any? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("role_id")
	val roleId: Int? = null,

	@field:SerializedName("isgoogleauth")
	val isgoogleauth: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class ArrivalTerminalWishlist(

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

data class TicketReturn(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("flight")
	val flight: Flight? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("class_id")
	val classId: Int? = null,

	@field:SerializedName("isRoundTrip")
	val isRoundTrip: Boolean? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("flight_id")
	val flightId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class DataItemWishlist(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("isWishlist")
	val isWishlist: Boolean? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("ticketReturn")
	val ticketReturn: TicketReturn? = null,

	@field:SerializedName("ticket_id_departure")
	val ticketIdDeparture: Int? = null,

	@field:SerializedName("ticketDeparture")
	val ticketDeparture: TicketDeparture? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("ticket_id_return")
	val ticketIdReturn: Int? = null,

	@field:SerializedName("users")
	val users: Users? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
