package com.ctwofinalproject.ticketing.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ctwofinalproject.ticketing.data.UserProto
import com.ctwofinalproject.ticketing.repository.BookingPreferencesRepository
import com.ctwofinalproject.ticketing.repository.UserPreferencesRepository
import kotlinx.coroutines.launch

class ProtoViewModel(application: Application) :AndroidViewModel(application) {
    private val repo = UserPreferencesRepository(application)
    private val repoBooking = BookingPreferencesRepository(application)
    val dataUser = repo.readProto.asLiveData()
    val dataBooking = repoBooking.readProto.asLiveData()

    fun editData(userProto: UserProto) = viewModelScope.launch{
        repo.saveData(userProto)
    }

    fun clearData() = viewModelScope.launch {
        repo.deleteData()
    }

    fun submitDataOneWay(ticketIdDeparture: String, passengerList: String, contactDetails: String, totalPrice: String) = viewModelScope.launch {
        repoBooking.saveOneWay(ticketIdDeparture, passengerList, contactDetails, totalPrice)
    }
    fun submitDataTwoWay(ticketIdDeparture: String,ticketIdReturn: String, passengerList: String, contactDetails: String, totalPrice: String) = viewModelScope.launch {
        repoBooking.saveTwoWay(ticketIdDeparture, ticketIdReturn, passengerList, contactDetails, totalPrice)
    }

    fun clearDataBooking() = viewModelScope.launch {
        repoBooking.clearData()
    }
}