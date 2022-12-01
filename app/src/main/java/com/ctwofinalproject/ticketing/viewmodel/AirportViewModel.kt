package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctwofinalproject.ticketing.api.RestServiceMain
import com.ctwofinalproject.ticketing.model.DataItem
import com.ctwofinalproject.ticketing.model.ResponseAirport
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AirportViewModel @Inject constructor(var api : RestServiceMain): ViewModel() {
    var liveDataAirport : MutableLiveData<ResponseAirport> = MutableLiveData()

    fun getDataAirport(): MutableLiveData<ResponseAirport> {
        return liveDataAirport
    }

    fun fetchAirport(token : String){
        val client = api.getAirport(token)
        client.enqueue(object : Callback<ResponseAirport> {
            override fun onResponse(
                call: Call<ResponseAirport>,
                response: Response<ResponseAirport>
            ) {
                if(response.isSuccessful){
                    liveDataAirport.postValue(response.body())
                } else {
                    liveDataAirport.postValue(null)
                }
            }
            override fun onFailure(call: Call<ResponseAirport>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

}