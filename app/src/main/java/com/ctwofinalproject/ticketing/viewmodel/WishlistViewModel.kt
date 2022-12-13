package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctwofinalproject.ticketing.api.RestServiceMain
import com.ctwofinalproject.ticketing.model.ResponseGetWishlist
import com.ctwofinalproject.ticketing.model.ResponseSearchFlight
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(var api: RestServiceMain): ViewModel() {
    var liveDataWishlist : MutableLiveData<ResponseGetWishlist?> = MutableLiveData()

    fun getWishlist(token: String) {
        val client = api.getWishlist(token)
        client.enqueue(object : Callback<ResponseGetWishlist> {
            override fun onResponse(
                call: Call<ResponseGetWishlist>,
                response: Response<ResponseGetWishlist>
            ) {
                if(response.isSuccessful){
                    liveDataWishlist.postValue(response.body())
                } else {
                    liveDataWishlist.value = null
                }
            }

            override fun onFailure(call: Call<ResponseGetWishlist>, t: Throwable) {
                Log.d(ContentValues.TAG, "onFailure: ${t.message}")
            }

        })
    }


}