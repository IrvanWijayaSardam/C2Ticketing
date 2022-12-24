package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctwofinalproject.ticketing.api.RestServiceMain
import com.ctwofinalproject.ticketing.model.ResponseGetBooking
import com.ctwofinalproject.ticketing.model.ResponseGetHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MyBookingViewModel @Inject constructor(var api : RestServiceMain): ViewModel() {
    var liveDataResponseGetBooking : MutableLiveData<ResponseGetBooking?> = MutableLiveData()
    var liveDataResponseGetHistory : MutableLiveData<ResponseGetHistory?> = MutableLiveData()

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

    fun getHistory(query: String, token: String){
        var client = api.getHistory(token,query)
        client.enqueue(object : Callback<ResponseGetHistory>{
            override fun onResponse(
                call: Call<ResponseGetHistory>,
                response: Response<ResponseGetHistory>
            ) {
                if(response.isSuccessful){
                    liveDataResponseGetHistory.postValue(response.body())
                } else {
                    liveDataResponseGetHistory.value = null
                }
            }

            override fun onFailure(call: Call<ResponseGetHistory>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }
}