package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctwofinalproject.ticketing.api.RestServiceMain
import com.ctwofinalproject.ticketing.model.ResponsePayment
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class PaymentViewModel @Inject constructor(var api : RestServiceMain):ViewModel() {
    var liveDataPayment : MutableLiveData<ResponsePayment?> = MutableLiveData()


    fun payBooking(token: String, idBooking: Int){
        var client = api.payBooking(token,idBooking)
        client.enqueue(object : Callback<ResponsePayment>{
            override fun onResponse(
                call: Call<ResponsePayment>,
                response: Response<ResponsePayment>
            ) {
                if(response.isSuccessful){
                    liveDataPayment.postValue(response.body())
                } else {
                    liveDataPayment.value = null
                }
            }

            override fun onFailure(call: Call<ResponsePayment>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

}