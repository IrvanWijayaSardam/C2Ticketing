package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctwofinalproject.ticketing.api.RestServiceMain
import com.ctwofinalproject.ticketing.model.ResponseGetSaldo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class FlitPayViewModel @Inject constructor(var api : RestServiceMain): ViewModel(){
    var liveDataSaldo : MutableLiveData<ResponseGetSaldo?> = MutableLiveData()


    fun getSaldo(token: String){
        val client = api.getSaldo(token)
        client.enqueue(object : Callback<ResponseGetSaldo>{
            override fun onResponse(
                call: Call<ResponseGetSaldo>,
                response: Response<ResponseGetSaldo>
            ) {
                if(response.isSuccessful){
                    liveDataSaldo.postValue(response.body())
                } else {
                    liveDataSaldo.value = null
                }
            }

            override fun onFailure(call: Call<ResponseGetSaldo>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }


}