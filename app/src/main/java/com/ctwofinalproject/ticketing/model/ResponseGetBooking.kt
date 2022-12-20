package com.ctwofinalproject.ticketing.model

import com.google.gson.annotations.SerializedName

data class ResponseGetBooking(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItemGetBooking?>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class TicketReturnGetBooking(

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

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("flight_id")
	val flightId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class TicketDepartureGetBooking(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("flight")
	val flight: FlightGetBooking? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("class_id")
	val classId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("flight_id")
	val flightId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class DataItemGetBooking(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("totalPrice")
	val totalPrice: Int? = null,

	@field:SerializedName("usersPayment")
	val usersPayment: UsersPayment? = null,

	@field:SerializedName("userBooking_id")
	val userBookingIdOne: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("isPayed")
	val isPayed: Boolean? = null,

	@field:SerializedName("UserBooking_id")
	val userBookingId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class PlaneNameItemGetBooking(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("namePlane")
	val namePlane: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class ArrivalTerminalGetBooking(

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

data class PassangerBookingItemGetBooking(

	@field:SerializedName("passanger")
	val passanger: List<PassangerItem?>? = null,

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

data class UsersPayment(

	@field:SerializedName("booking_id")
	val bookingId: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("booking")
	val booking: BookingGetBooking? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class PassangerItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("firstname")
	val firstname: String? = null,

	@field:SerializedName("identityType")
	val identityType: String? = null,

	@field:SerializedName("identityNumber")
	val identityNumber: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("age")
	val age: Int? = null,

	@field:SerializedName("lastname")
	val lastname: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class FlightGetBooking(

	@field:SerializedName("departureTime")
	val departureTime: String? = null,

	@field:SerializedName("departureAirport")
	val departureAirport: Int? = null,

	@field:SerializedName("DepartureTerminal")
	val departureTerminal: DepartureTerminalGetBooking? = null,

	@field:SerializedName("ArrivalTerminal")
	val arrivalTerminal: ArrivalTerminalGetBooking? = null,

	@field:SerializedName("arrivalDate")
	val arrivalDate: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("planeName")
	val planeName: List<PlaneNameItemGetBooking?>? = null,

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

data class BookingGetBooking(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("isBooking")
	val isBooking: Boolean? = null,

	@field:SerializedName("ticketReturn")
	val ticketReturn: TicketReturnGetBooking? = null,

	@field:SerializedName("ticket_id_departure")
	val ticketIdDeparture: Int? = null,

	@field:SerializedName("totalPassanger")
	val totalPassanger: Int? = null,

	@field:SerializedName("ticketDeparture")
	val ticketDeparture: TicketDepartureGetBooking? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("ticket_id_return")
	val ticketIdReturn: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null,

	@field:SerializedName("passangerBooking")
	val passangerBooking: List<PassangerBookingItemGetBooking?>? = null
)

data class DepartureTerminalGetBooking(

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
