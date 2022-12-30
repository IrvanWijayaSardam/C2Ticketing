package com.ctwofinalproject.ticketing.model

import com.google.gson.annotations.SerializedName

data class ResponseGetTicketById(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: DataTicketGetById? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class ArrivalTerminalGetTicketByID(

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

data class PlaneNameItemResponseGetTicketById(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("namePlane")
	val namePlane: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class JsonMemberClassResponseGetTicketByID(

	@field:SerializedName("type")
	val type: String? = null
)

data class FlightResponseGetTicketById(

	@field:SerializedName("departureTime")
	val departureTime: String? = null,

	@field:SerializedName("departureAirport")
	val departureAirport: Int? = null,

	@field:SerializedName("DepartureTerminal")
	val departureTerminal: DepartureTerminalResponseGetTicketById? = null,

	@field:SerializedName("ArrivalTerminal")
	val arrivalTerminal: ArrivalTerminalGetTicketByID? = null,

	@field:SerializedName("arrivalDate")
	val arrivalDate: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("planeName")
	val planeName: List<PlaneNameItemResponseGetTicketById?>? = null,

	@field:SerializedName("arrivalTime")
	val arrivalTime: String? = null,

	@field:SerializedName("planeId")
	val planeId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("departureDate")
	val departureDate: String? = null,

	@field:SerializedName("arrivalAirport")
	val arrivalAirport: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class DepartureTerminalResponseGetTicketById(

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

data class DataTicketGetById(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("flight")
	val flight: FlightResponseGetTicketById? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("class_id")
	val classId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("class")
	val jsonMemberClass: JsonMemberClassResponseGetTicketByID? = null,

	@field:SerializedName("flight_id")
	val flightId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)