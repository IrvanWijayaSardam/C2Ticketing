package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctwofinalproject.ticketing.api.RestServiceMain
import com.ctwofinalproject.ticketing.data.User
import com.ctwofinalproject.ticketing.model.ResponseMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(var api : RestServiceMain):ViewModel() {
    var statusRegist = MutableLiveData<ResponseMessage?>()

    init {
        statusRegist = MutableLiveData()
    }

    fun getStatusRegist() : LiveData<ResponseMessage?>{
        return statusRegist
    }

    fun registUser(user: User){
        val client = api.createUser(user)
        client.enqueue(object : Callback<ResponseMessage>{
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                if(response.isSuccessful){
                    statusRegist.postValue(response.body())
                } else {
                    statusRegist.value = null
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
}