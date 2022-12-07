package com.ctwofinalproject.ticketing.data


import com.google.gson.annotations.SerializedName

data class Ticket(
    @SerializedName("ticket_id_departure")
    val ticketIdDeparture: Int?,
    @SerializedName("ticket_id_return")
    val ticketIdReturn: Int?,
    @SerializedName("totalPrice")
    val totalPrice: Int?
)