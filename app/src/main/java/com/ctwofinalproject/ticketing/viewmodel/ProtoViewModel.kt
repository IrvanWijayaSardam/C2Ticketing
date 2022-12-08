package com.ctwofinalproject.ticketing.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ctwofinalproject.ticketing.data.UserProto
import com.ctwofinalproject.ticketing.repository.UserPreferencesRepository
import kotlinx.coroutines.launch

class ProtoViewModel(application: Application) :AndroidViewModel(application) {
    private val repo = UserPreferencesRepository(application)
    val dataUser = repo.readProto.asLiveData()

    fun editData(userProto: UserProto) = viewModelScope.launch{
        repo.saveData(userProto)
    }

    fun clearData() = viewModelScope.launch {
        repo.deleteData()
    }
}