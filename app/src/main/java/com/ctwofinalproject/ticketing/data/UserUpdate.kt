package com.ctwofinalproject.ticketing.data

import com.google.gson.annotations.SerializedName

class UserUpdate (
        @SerializedName("email")
        val email: String?,
        @SerializedName("firstname")
        val firstname: String?,
        @SerializedName("lastname")
        val lastname: String?,
        @SerializedName("gender")
        val gender: String?,
        @SerializedName("phone")
        val phone: String?,
        @SerializedName("birthdate")
        val birthdate: String?,
        @SerializedName("password")
        val password: String?,
        @SerializedName("confPassword")
        val confPassword: String?,
        @SerializedName("homeAddress")
        val homeAddress: String?,
        @SerializedName("country")
        val country: String?,
        @SerializedName("province")
        val province: String?,
        @SerializedName("city")
        val city: String?
)