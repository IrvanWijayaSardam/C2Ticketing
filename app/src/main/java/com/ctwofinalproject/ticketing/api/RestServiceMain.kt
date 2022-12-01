package com.ctwofinalproject.ticketing.api

import android.provider.ContactsContract.Data
import com.ctwofinalproject.ticketing.data.Login
import com.ctwofinalproject.ticketing.data.User
import com.ctwofinalproject.ticketing.model.DataItem
import com.ctwofinalproject.ticketing.model.ResponseAirport
import com.ctwofinalproject.ticketing.model.ResponseLogin
import com.ctwofinalproject.ticketing.model.ResponseMessage
import retrofit2.Call
import retrofit2.http.*

interface RestServiceMain {

    @POST("api/register")
    fun createUser(
        @Body body :User
    ) : Call<ResponseMessage>

    @POST("api/login")
    fun auth(
        @Body body: Login
    ) : Call<ResponseLogin>

    @GET("api/airports")
    fun getAirport(
        @Header("Authorization") authorization : String
    ) : Call<ResponseAirport>
}