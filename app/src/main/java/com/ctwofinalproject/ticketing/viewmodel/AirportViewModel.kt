package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctwofinalproject.ticketing.api.RestServiceMain
import com.ctwofinalproject.ticketing.entity.Airport
import com.ctwofinalproject.ticketing.model.DataItem
import com.ctwofinalproject.ticketing.model.ResponseAirport
import com.ctwofinalproject.ticketing.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AirportViewModel @Inject constructor(var api : RestServiceMain, var roomRepository: RoomRepository): ViewModel() {
    var liveDataAirport : MutableLiveData<ResponseAirport?> = MutableLiveData()
    var liveDataAirportSearch : MutableLiveData<ResponseAirport?> = MutableLiveData()

    fun getDataAirport(): MutableLiveData<ResponseAirport?> {
        return liveDataAirport
    }
    fun getDataAirportSearch(): MutableLiveData<ResponseAirport?> {
        return liveDataAirportSearch
    }

    fun fetchAirport(){
        val client = api.getAirport()
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

    fun searchAirport(token: String, query: String){
        val client = api.searchAirport(token, query)
        client.enqueue(object : Callback<ResponseAirport> {
            override fun onResponse(
                call: Call<ResponseAirport>,
                response: Response<ResponseAirport>
            ) {
                if(response.isSuccessful){
                    liveDataAirportSearch.postValue(response.body())
                } else {
                    liveDataAirportSearch.postValue(null)
                }
            }
            override fun onFailure(call: Call<ResponseAirport>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })

    }

    fun insert(airport: Airport){
        viewModelScope.launch {
            roomRepository.insertAirport(airport)
        }
    }

    fun getAllAirport() : LiveData<List<Airport>> = roomRepository.getAllAirport()

}