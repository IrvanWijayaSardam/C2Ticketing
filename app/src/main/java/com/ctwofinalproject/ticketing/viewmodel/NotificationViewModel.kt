package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctwofinalproject.ticketing.api.RestServiceMain
import com.ctwofinalproject.ticketing.model.ResponseGetNotificationUnread
import com.ctwofinalproject.ticketing.model.ResponseReadNotification
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.chrono.MinguoDate
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(var api : RestServiceMain): ViewModel() {
    var liveDataUnreadNotification : MutableLiveData<ResponseGetNotificationUnread?> = MutableLiveData()
    var liveDataReadNotification : MutableLiveData<ResponseReadNotification?> = MutableLiveData()

    fun getUnreadNotification(token : String,status: String?){
        val client = api.getNotificationUnread(token,status)
        client.enqueue(object : Callback<ResponseGetNotificationUnread> {
            override fun onResponse(
                call: Call<ResponseGetNotificationUnread>,
                response: Response<ResponseGetNotificationUnread>
            ) {
                if(response.isSuccessful){
                    liveDataUnreadNotification.postValue(response.body())
                } else {
                    liveDataUnreadNotification.value = null
                }
            }

            override fun onFailure(call: Call<ResponseGetNotificationUnread>, t: Throwable) {
                Log.d(ContentValues.TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun readAllNotification(token: String){
        val client = api.readAllNotification(token)
        client.enqueue(object : Callback<ResponseReadNotification>{
            override fun onResponse(
                call: Call<ResponseReadNotification>,
                response: Response<ResponseReadNotification>
            ) {
                if(response.isSuccessful){
                    liveDataReadNotification.postValue(response.body())
                } else {
                    liveDataReadNotification.value = null
                }
            }

            override fun onFailure(call: Call<ResponseReadNotification>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

}