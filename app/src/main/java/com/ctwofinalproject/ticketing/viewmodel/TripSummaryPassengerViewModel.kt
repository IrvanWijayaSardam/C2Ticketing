package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctwofinalproject.ticketing.api.RestServiceMain
import com.ctwofinalproject.ticketing.data.TicketData
import com.ctwofinalproject.ticketing.model.ResponseBooking
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TripSummaryPassengerViewModel @Inject constructor(var api: RestServiceMain): ViewModel() {
    var statusSubmitBooking = MutableLiveData<ResponseBooking?>()

    init {
        statusSubmitBooking = MutableLiveData()
    }

    fun submitBooking(token: String,ticketData: TicketData){
        val client = api.submitBooking(token,ticketData)
        client.enqueue(object : Callback<ResponseBooking>{
            override fun onResponse(
                call: Call<ResponseBooking>,
                response: Response<ResponseBooking>
            ) {
                if(response.isSuccessful){
                    statusSubmitBooking.postValue(response.body())
                } else {
                    statusSubmitBooking.value = null
                }
            }

            override fun onFailure(call: Call<ResponseBooking>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

}