package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.ctwofinalproject.ticketing.api.RestServiceMain
import com.ctwofinalproject.ticketing.api.RestServiceProvince
import com.ctwofinalproject.ticketing.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(var api : RestServiceMain):ViewModel() {


    fun registUser(user: User){
        val client = api.createUser(user)
        client.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.d(ContentValues.TAG, "onResponse: uSER Inserted")
                    Log.e(ContentValues.TAG, "onSuccess: ${responseBody}")
                    //dialog.dismiss()

            }else{
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")

                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")

            }
        })
    }
}