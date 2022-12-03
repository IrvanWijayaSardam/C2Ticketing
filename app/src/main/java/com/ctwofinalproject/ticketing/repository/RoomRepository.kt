package com.ctwofinalproject.ticketing.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ctwofinalproject.ticketing.daos.AirportDAO
import com.ctwofinalproject.ticketing.db.TicketingDB
import com.ctwofinalproject.ticketing.entity.Airport
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

class RoomRepository @Inject constructor (private val airportDAO: AirportDAO) {

    fun insertAirport(airport: Airport){
        TicketingDB.databaseWriteExecutor.execute {
            airportDAO.insertAirport(airport)
        }
    }

    fun getAllAirport() : LiveData<List<Airport>> = airportDAO.getAllAirport()


}