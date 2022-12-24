package com.ctwofinalproject.ticketing.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue

@Parcelize
data class ResponseGetHistory(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItemHistory?>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class WishlistItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("isWishlist")
	val isWishlist: Boolean? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("ticket_id_departure")
	val ticketIdDeparture: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("ticket_id_return")
	val ticketIdReturn: @RawValue Any? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable

@Parcelize
data class Address(

	@field:SerializedName("country")
	val country: @RawValue Any? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("province")
	val province: @RawValue Any? = null,

	@field:SerializedName("city")
	val city: @RawValue Any? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("homeAddress")
	val homeAddress: @RawValue Any? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable

@Parcelize
data class BookingHistory(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("isBooking")
	val isBooking: Boolean? = null,

	@field:SerializedName("ticketReturn")
	val ticketReturn: TicketReturnHistory? = null,

	@field:SerializedName("ticket_id_departure")
	val ticketIdDeparture: Int? = null,

	@field:SerializedName("totalPassanger")
	val totalPassanger: Int? = null,

	@field:SerializedName("ticketDeparture")
	val ticketDeparture: TicketDepartureHistory? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("ticket_id_return")
	val ticketIdReturn: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null,

	@field:SerializedName("passangerBooking")
	val passangerBooking: List<PassangerBookingItemHistory?>? = null
) : Parcelable

@Parcelize
data class UsersHistory(

	@field:SerializedName("firstname")
	val firstname: String? = null,

	@field:SerializedName("birthdate")
	val birthdate: String? = null,

	@field:SerializedName("address")
	val address: Address? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("wishlist")
	val wishlist: List<WishlistItem?>? = null,

	@field:SerializedName("roles")
	val roles: Roles? = null,

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
	val isfacebookauth: @RawValue Any? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("role_id")
	val roleId: Int? = null,

	@field:SerializedName("isgoogleauth")
	val isgoogleauth: @RawValue Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable

@Parcelize
data class UserBookingHistory(

	@field:SerializedName("booking_id")
	val bookingId: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("booking")
	val booking: BookingHistory? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("users")
	val users: UsersHistory? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable

@Parcelize
data class TicketReturnHistory(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

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
) : Parcelable

@Parcelize
data class DataItemHistory(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("userBooking")
	val userBooking: UserBookingHistory? = null,

	@field:SerializedName("userBooking_id")
	val userBookingId: Int? = null,

	@field:SerializedName("payment_id")
	val paymentId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("isHistory")
	val isHistory: Boolean? = null,

	@field:SerializedName("paymentType")
	val paymentType: @RawValue Any? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable

@Parcelize
data class TicketDepartureHistory(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

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
) : Parcelable

@Parcelize
data class PassangerBookingItemHistory(

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
) : Parcelable

@Parcelize
data class Roles(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("roleName")
	val roleName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable
