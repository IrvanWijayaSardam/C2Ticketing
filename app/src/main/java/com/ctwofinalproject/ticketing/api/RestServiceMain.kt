package com.ctwofinalproject.ticketing.api

import android.provider.ContactsContract.Data
import com.ctwofinalproject.ticketing.data.*
import com.ctwofinalproject.ticketing.model.*
import okhttp3.MultipartBody
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


    @POST("api/booking")
    fun submitBooking(
        @Header("Authorization") authorization : String,
        @Body body: TicketData
    ) : Call<ResponseBooking>

    @GET("api/airports")
    fun getAirport(
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
        @Query("datedeparture") dateDeparture: String,
    ) : Call<ResponseSearchFlight>

    @GET("api/tickets/{ticketNumber}")
    fun getTicketById(
        @Path("ticketNumber") ticketNumber: String
    ) : Call<ResponseGetTicketById>

    @GET("api/wishlists")
    fun getWishlist(
        @Header("Authorization") authorization: String
    )  : Call<ResponseGetWishlist>

    @DELETE("api/logout")
    fun logout(
        @Header("Authorization") authorization: String
    ) : Call<String>

    @POST("api/upload")
    @Multipart
    fun postImage (@Part file : MultipartBody.Part) : Call<ResponsePostFile>

    @GET("api/whoami")
    fun whoami (
        @Header("Authorization") authorization: String
    ) : Call<ResponseWhoami>

    @PUT("api/users/profile")
    fun putUser(
        @Header("Authorization") authorization: String,
        @Body body : UserUpdate
    ) : Call<ResponseUpdate>

    @POST("api/wishlists/create")
    fun createWishlist(
        @Header("Authorization") authorization: String,
        @Body body: CreateWishlist
    ) : Call<ResponseCreateWishlist>

    @PUT("api/users/profile/password")
    fun updatePassword(
        @Header("Authorization") authorization: String,
        @Body body : UpdatePassword
    ) : Call<ResponseUpdatePassword>

    @GET("api/payments")
    fun getBooking(
        @Header("Authorization") authorization: String,
    ) : Call<ResponseGetBooking>
}