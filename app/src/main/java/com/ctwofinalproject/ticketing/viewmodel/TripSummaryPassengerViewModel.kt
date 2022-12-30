package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.api.RestServiceMain
import com.ctwofinalproject.ticketing.data.CreateWishlist
import com.ctwofinalproject.ticketing.data.TicketData
import com.ctwofinalproject.ticketing.model.ResponseBooking
import com.ctwofinalproject.ticketing.model.ResponseCreateWishlist
import com.ctwofinalproject.ticketing.model.ResponseGetTicketById
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TripSummaryPassengerViewModel @Inject constructor(var api: RestServiceMain): ViewModel() {
    var statusSubmitBooking       = MutableLiveData<ResponseBooking?>()
    var dataTicketById            = MutableLiveData<ResponseGetTicketById?>()
    var dataTicketReturnById      = MutableLiveData<ResponseGetTicketById?>()
    var dataResponseWishlist      = MutableLiveData<ResponseCreateWishlist?>()

    init {
        statusSubmitBooking     = MutableLiveData()
        dataTicketById          = MutableLiveData()
        dataTicketReturnById    = MutableLiveData()
        dataResponseWishlist    = MutableLiveData()
    }

    fun getStatusBooking(): LiveData<ResponseBooking?> {
        return statusSubmitBooking
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

    fun getTicketById(ticketNumber: String){
        val client = api.getTicketById(ticketNumber)
        client.enqueue(object : Callback<ResponseGetTicketById>{
            override fun onResponse(
                call: Call<ResponseGetTicketById>,
                response: Response<ResponseGetTicketById>
            ) {
                if(response.isSuccessful){
                    dataTicketById.postValue(response.body())
                } else {
                    dataTicketById.value = null
                }
            }
            override fun onFailure(call: Call<ResponseGetTicketById>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getTicketReturnById(ticketNumber: String){
        val client = api.getTicketById(ticketNumber)
        client.enqueue(object : Callback<ResponseGetTicketById>{
            override fun onResponse(
                call: Call<ResponseGetTicketById>,
                response: Response<ResponseGetTicketById>
            ) {
                if(response.isSuccessful){
                    dataTicketReturnById.postValue(response.body())
                } else {
                    dataTicketReturnById.value = null
                }
            }

            override fun onFailure(call: Call<ResponseGetTicketById>, t: Throwable) {
                dataTicketReturnById.value = null
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun postWishlist(token: String,body: CreateWishlist){
        val client = api.createWishlist(token,body)
        client.enqueue(object : Callback<ResponseCreateWishlist>{
            override fun onResponse(
                call: Call<ResponseCreateWishlist>,
                response: Response<ResponseCreateWishlist>
            ) {
                if(response.isSuccessful){
                    Log.d(TAG, "onResponse: ${response.body()}")
                    dataResponseWishlist.postValue(response.body())
                } else {
                    dataResponseWishlist.value = null
                }
            }
            override fun onFailure(call: Call<ResponseCreateWishlist>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }


}