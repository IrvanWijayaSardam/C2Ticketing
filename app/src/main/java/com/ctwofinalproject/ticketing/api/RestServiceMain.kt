package com.ctwofinalproject.ticketing.api

import com.ctwofinalproject.ticketing.data.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RestServiceMain {
    @POST("api/auth/register")
    fun createUser(
        @Body body :User
    ) : Call<User>

}