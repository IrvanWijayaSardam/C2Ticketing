package com.ctwofinalproject.ticketing.api

import android.provider.ContactsContract.Data
import com.ctwofinalproject.ticketing.data.Login
import com.ctwofinalproject.ticketing.data.User
import com.ctwofinalproject.ticketing.model.*
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

    @GET("api/airports/{query}")
    fun searchAirport(
        @Header("Authorization") authorization : String,
        @Path("query") query : String
    ) : Call<ResponseAirport>

    @GET("api/tickets/search")
    fun searchTicket(
        @Query("departure") departure: String,
        @Query("arrival") arrival: String,
        @Query("datedeparture") dateDeparture: String
        ) : Call<ResponseSearchFlight>
}