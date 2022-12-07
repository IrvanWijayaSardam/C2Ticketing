package com.ctwofinalproject.ticketing.data


import com.google.gson.annotations.SerializedName

data class Passanger(
    @SerializedName("age")
    val age: Int?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("firstname")
    val firstname: String?,
    @SerializedName("identityNumber")
    val identityNumber: String?,
    @SerializedName("identityType")
    val identityType: String?,
    @SerializedName("lastname")
    val lastname: String?
)