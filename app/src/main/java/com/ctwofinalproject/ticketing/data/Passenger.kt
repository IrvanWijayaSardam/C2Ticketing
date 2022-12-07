package com.ctwofinalproject.ticketing.data

import com.google.gson.annotations.SerializedName

data class Passenger(
    @SerializedName("title")
    val title: String?,
    @SerializedName("firstname")
    val firstname: String?,
    @SerializedName("lastname")
    val lastname: String?,
    @SerializedName("nik")
    val nik: String?,
)
