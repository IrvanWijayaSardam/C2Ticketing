package com.ctwofinalproject.ticketing.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctwofinalproject.ticketing.entity.Airport
import com.ctwofinalproject.ticketing.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirportRoomViewModel @Inject constructor(private val roomRepository: RoomRepository) : ViewModel() {
    fun insert(airport: Airport){
        viewModelScope.launch {
            roomRepository.insertAirport(airport)
        }
    }

    fun getAllAirport() : LiveData<List<Airport>> = roomRepository.getAllAirport()

}