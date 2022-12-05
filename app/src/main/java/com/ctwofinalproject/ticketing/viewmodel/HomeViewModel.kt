package com.ctwofinalproject.ticketing.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctwofinalproject.ticketing.entity.RecentSearch
import com.ctwofinalproject.ticketing.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(var roomRepository: RoomRepository): ViewModel() {

    fun getAllRecentSearch() : LiveData<List<RecentSearch>> = roomRepository.getAllRecentSearch()

    fun deleteAllRecentSearch(){
        viewModelScope.launch {
            roomRepository.deleteAllRecentSearch()
        }
    }
}