package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctwofinalproject.ticketing.api.RestServiceMain
import com.ctwofinalproject.ticketing.model.ResponseSearchFlight
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ShowTicketViewModel @Inject constructor(var api: RestServiceMain): ViewModel() {
    var liveDataFlight : MutableLiveData<ResponseSearchFlight?> = MutableLiveData()
    var liveDataFlightReturn : MutableLiveData<ResponseSearchFlight?> = MutableLiveData()


    fun getDataFlight(): MutableLiveData<ResponseSearchFlight?> {
        return  liveDataFlight
    }

    fun searchTicket(departure : String, arrival : String, dateDeparture: String){
        val client = api.searchTicket(departure, arrival, dateDeparture)
        client.enqueue(object : Callback<ResponseSearchFlight>{
            override fun onResponse(
                call: Call<ResponseSearchFlight>,
                response: Response<ResponseSearchFlight>
            ) {
                if(response.isSuccessful){
                    liveDataFlight.postValue(response.body())
                } else {
                    liveDataFlight.value = null
                }
            }

            override fun onFailure(call: Call<ResponseSearchFlight>, t: Throwable) {
                Log.d(ContentValues.TAG, "onFailure: ${t.message}")
            }

        })
    }
    fun searchTicketReturn(departure : String, arrival : String, dateDeparture: String){
        val client = api.searchTicket(departure, arrival, dateDeparture)
        client.enqueue(object : Callback<ResponseSearchFlight>{
            override fun onResponse(
                call: Call<ResponseSearchFlight>,
                response: Response<ResponseSearchFlight>
            ) {
                if(response.isSuccessful){
                    liveDataFlightReturn.postValue(response.body())
                } else {
                    liveDataFlightReturn.value = null
                }
            }

            override fun onFailure(call: Call<ResponseSearchFlight>, t: Throwable) {
                Log.d(ContentValues.TAG, "onFailure: ${t.message}")
            }

        })
    }

}