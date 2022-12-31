package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctwofinalproject.ticketing.api.RestServiceMain
import com.ctwofinalproject.ticketing.data.ResponseUpdatePassword
import com.ctwofinalproject.ticketing.data.UpdatePassword
import com.ctwofinalproject.ticketing.data.UserUpdate
import com.ctwofinalproject.ticketing.model.ResponsePostFile
import com.ctwofinalproject.ticketing.model.ResponseUpdate
import com.ctwofinalproject.ticketing.model.ResponseWhoami
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(var api: RestServiceMain): ViewModel() {
    var liveDataResponsePostFile : MutableLiveData<ResponsePostFile?> = MutableLiveData()
    var liveDataResponseWhoami : MutableLiveData<ResponseWhoami?> = MutableLiveData()
    var liveDataResponseUpdate : MutableLiveData<ResponseUpdate?> = MutableLiveData()
    var liveDataResponseUpdatePassword : MutableLiveData<ResponseUpdatePassword?> = MutableLiveData()


    fun getResponse() : LiveData<ResponsePostFile?> {
        return  liveDataResponsePostFile
    }

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

    fun whoami(token: String){
        val client = api.whoami(token)
        client.enqueue(object : Callback<ResponseWhoami> {
            override fun onResponse(
                call: Call<ResponseWhoami>,
                response: Response<ResponseWhoami>
            ) {
                if (response.isSuccessful){
                    liveDataResponseWhoami.postValue(response.body())
                } else {
                    liveDataResponseWhoami.value = null
                }
            }

            override fun onFailure(call: Call<ResponseWhoami>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun updateUser(token: String,userUpdate: UserUpdate){
        val client = api.putUser(token,userUpdate)
        client.enqueue(object : Callback<ResponseUpdate> {
            override fun onResponse(
                call: Call<ResponseUpdate>,
                response: Response<ResponseUpdate>
            ) {
                if(response.isSuccessful){
                    liveDataResponseUpdate.postValue(response.body())
                } else {
                    liveDataResponseUpdate.value = null
                }
            }

            override fun onFailure(call: Call<ResponseUpdate>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun updatePassword(token: String,updatePassword: UpdatePassword){
        val client = api.updatePassword(token,updatePassword)
        client.enqueue(object : Callback<ResponseUpdatePassword> {
            override fun onResponse(
                call: Call<ResponseUpdatePassword>,
                response: Response<ResponseUpdatePassword>
            ) {
                if(response.isSuccessful){
                    liveDataResponseUpdatePassword.postValue(response.body())
                } else {
                    liveDataResponseUpdatePassword.value = null
                }
            }

            override fun onFailure(call: Call<ResponseUpdatePassword>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
}