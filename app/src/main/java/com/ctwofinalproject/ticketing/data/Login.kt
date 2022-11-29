package com.ctwofinalproject.ticketing.data

import com.google.gson.annotations.SerializedName

class Login (
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?
)