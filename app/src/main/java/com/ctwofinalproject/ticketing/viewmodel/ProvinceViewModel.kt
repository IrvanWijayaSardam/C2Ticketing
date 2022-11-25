package com.ctwofinalproject.ticketing.viewmodel

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctwofinalproject.ticketing.api.RestServiceProvince
import com.ctwofinalproject.ticketing.data.ResponseProvinceItem
import com.ctwofinalproject.ticketing.model.ResponseCityItem
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProvinceViewModel @Inject constructor(var api : RestServiceProvince): ViewModel() {
    var liveDataProvince : MutableLiveData<List<ResponseProvinceItem>>
    var liveDataCity : MutableLiveData<List<ResponseCityItem>>

    init{
        liveDataProvince = MutableLiveData()
        liveDataCity = MutableLiveData()
    }

    fun getLiveDataProvinces() : LiveData<List<ResponseProvinceItem>?> {
        return liveDataProvince
    }

    fun getLiveDataCity() : LiveData<List<ResponseCityItem>?>{
        return liveDataCity
    }

    fun retrieveProvince(){
        val client = api.getProvinces()
        client.enqueue(object : Callback<List<ResponseProvinceItem>> {
            override fun onResponse(
                call: Call<List<ResponseProvinceItem>>,
                response: Response<List<ResponseProvinceItem>>
            ) {
                val responseBody = response.body()!!
                if(responseBody != null){
                    Log.d(ContentValues.TAG, "onResponse: ${responseBody}")
                    liveDataProvince.postValue(response.body())
                } else {
                    Log.d(ContentValues.TAG, "onResponse Else: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ResponseProvinceItem>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailureViewModel: ${t.message}")
            }
        })
    }


    fun retrieveCity(id : Int){
        val client = api.getCity(id)
        client.enqueue(object : Callback<List<ResponseCityItem>> {
            override fun onResponse(
                call: Call<List<ResponseCityItem>>,
                response: Response<List<ResponseCityItem>>
            ) {
                val responseBody = response.body()
                if(responseBody != null){
                    Log.d(TAG, "onResponse: ${responseBody} ")
                    liveDataCity.postValue(response.body())
                } else {
                    Log.d(TAG, "onResponse Else: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ResponseCityItem>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailureViewModel: ${t.message}")
            }

        })

    }

}

