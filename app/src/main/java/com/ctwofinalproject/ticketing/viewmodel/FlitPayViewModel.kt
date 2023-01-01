package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctwofinalproject.ticketing.api.RestServiceMain
import com.ctwofinalproject.ticketing.data.BalanceBody
import com.ctwofinalproject.ticketing.model.ResponseGetSaldo
import com.ctwofinalproject.ticketing.model.ResponseTopup
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class FlitPayViewModel @Inject constructor(var api : RestServiceMain): ViewModel(){
    var liveDataSaldo : MutableLiveData<ResponseGetSaldo?> = MutableLiveData()
    var liveDataResponseTopup : MutableLiveData<ResponseTopup?> = MutableLiveData()

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

    fun topupSaldo(token: String,idUser: Int,balanceBody: BalanceBody){
        val client = api.topupSaldo(token,idUser,balanceBody)
        client.enqueue(object : Callback<ResponseTopup>{
            override fun onResponse(call: Call<ResponseTopup>, response: Response<ResponseTopup>) {
                if(response.isSuccessful){
                    liveDataResponseTopup.postValue(response.body())
                } else {
                    liveDataResponseTopup.value = null
                }
            }

            override fun onFailure(call: Call<ResponseTopup>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }


}