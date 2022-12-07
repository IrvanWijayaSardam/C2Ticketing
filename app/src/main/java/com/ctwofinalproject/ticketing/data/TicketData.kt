package com.ctwofinalproject.ticketing.data


import com.google.gson.annotations.SerializedName

data class TicketData(
    @SerializedName("passanger")
    val passanger: List<Passanger?>?,
    @SerializedName("ticket")
    val ticket: Ticket?
)