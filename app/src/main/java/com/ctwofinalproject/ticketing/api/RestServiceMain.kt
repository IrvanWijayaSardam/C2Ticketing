package com.ctwofinalproject.ticketing.api

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

    @FormUrlEncoded
    @POST("api/login")
    fun auth(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<ResponseLogin>
}