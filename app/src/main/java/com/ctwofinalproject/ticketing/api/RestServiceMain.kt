package com.ctwofinalproject.ticketing.api

import com.ctwofinalproject.ticketing.data.Login
import com.ctwofinalproject.ticketing.data.User
import com.ctwofinalproject.ticketing.model.ResponseLogin
import com.ctwofinalproject.ticketing.model.ResponseMessage
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface RestServiceMain {

    @POST("api/register")
    fun createUser(
        @Body body :User
    ) : Call<ResponseMessage>

    @POST("api/login")
    fun auth(
        @Body body: Login
    ) : Call<ResponseLogin>
}