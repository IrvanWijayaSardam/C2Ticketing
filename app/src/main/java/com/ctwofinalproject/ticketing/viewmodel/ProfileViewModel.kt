package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctwofinalproject.ticketing.api.RestServiceMain
import com.ctwofinalproject.ticketing.model.ResponsePostFile
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(var api: RestServiceMain): ViewModel() {
    var liveDataResponsePostFile : MutableLiveData<ResponsePostFile?> = MutableLiveData()

    fun logout(token: String){
        val client = api.logout(token)
        client.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val responseBody = response.body()
                if(response.isSuccessful){
                    Log.d(TAG, "onResponse: Logout Success")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun postFile(file: MultipartBody.Part){
        val client = api.postImage(file)
        client.enqueue(object : Callback<ResponsePostFile> {
            override fun onResponse(
                call: Call<ResponsePostFile>,
                response: Response<ResponsePostFile>
            ) {
                if(response.isSuccessful){
                    liveDataResponsePostFile.postValue(response.body())
                } else {
                    liveDataResponsePostFile.value = null
                }
            }

            override fun onFailure(call: Call<ResponsePostFile>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
}