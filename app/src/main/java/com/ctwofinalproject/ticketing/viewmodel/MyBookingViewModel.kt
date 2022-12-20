package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctwofinalproject.ticketing.api.RestServiceMain
import com.ctwofinalproject.ticketing.model.ResponseGetBooking
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MyBookingViewModel @Inject constructor(var api : RestServiceMain): ViewModel() {
    var liveDataResponseGetBooking : MutableLiveData<ResponseGetBooking?> = MutableLiveData()

    fun getBooking(token: String){
        var client = api.getBooking(token)
        client.enqueue(object : Callback<ResponseGetBooking> {
            override fun onResponse(
                call: Call<ResponseGetBooking>,
                response: Response<ResponseGetBooking>
            ) {
                if(response.isSuccessful){
                    liveDataResponseGetBooking.postValue(response.body())
                } else {
                    liveDataResponseGetBooking.value = null
                }
            }

            override fun onFailure(call: Call<ResponseGetBooking>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
}