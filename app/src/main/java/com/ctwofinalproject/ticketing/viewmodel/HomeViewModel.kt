package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctwofinalproject.ticketing.api.RestServiceMain
import com.ctwofinalproject.ticketing.entity.RecentSearch
import com.ctwofinalproject.ticketing.model.ResponseGetNotificationUnread
import com.ctwofinalproject.ticketing.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(var api : RestServiceMain,var roomRepository: RoomRepository): ViewModel() {
    var liveDataGetNotificationCounter : MutableLiveData<ResponseGetNotificationUnread?> = MutableLiveData()


    fun getAllRecentSearch() : LiveData<List<RecentSearch>> = roomRepository.getAllRecentSearch()

    fun deleteAllRecentSearch(){
        viewModelScope.launch {
            roomRepository.deleteAllRecentSearch()
        }
    }

    fun getCounter(token : String){
        val client = api.getNotificationUnread(token,"")
        client.enqueue(object : Callback<ResponseGetNotificationUnread>{
            override fun onResponse(
                call: Call<ResponseGetNotificationUnread>,
                response: Response<ResponseGetNotificationUnread>
            ) {
                if(response.isSuccessful){
                    liveDataGetNotificationCounter.postValue(response.body())
                } else {
                    liveDataGetNotificationCounter.value = null
                }
            }

            override fun onFailure(call: Call<ResponseGetNotificationUnread>, t: Throwable) {
                Log.d(ContentValues.TAG, "onFailure: ${t.message}")
            }

        })
    }
}