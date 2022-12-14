package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.ctwofinalproject.ticketing.api.RestServiceMain
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(var api: RestServiceMain): ViewModel() {

    fun logout(token: String){
        val client = api.logout(token)
        client.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val responseBody = response.body()
                if(response.isSuccessful){
                    Log.d(TAG, "onResponse: Logout Success")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
}