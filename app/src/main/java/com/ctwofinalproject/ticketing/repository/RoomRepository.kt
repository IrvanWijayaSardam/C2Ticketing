package com.ctwofinalproject.ticketing.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ctwofinalproject.ticketing.daos.AirportDAO
import com.ctwofinalproject.ticketing.daos.RecentSearchDAO
import com.ctwofinalproject.ticketing.db.TicketingDB
import com.ctwofinalproject.ticketing.entity.Airport
import com.ctwofinalproject.ticketing.entity.RecentSearch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

class RoomRepository @Inject constructor (private val airportDAO: AirportDAO,private val recentSearchDAO: RecentSearchDAO) {

    fun insertAirport(airport: Airport){
        TicketingDB.databaseWriteExecutor.execute {
            airportDAO.insertAirport(airport)
        }
    }

    fun insertRecentSearch(recentSearch: RecentSearch){
        TicketingDB.databaseWriteExecutor.execute {
            recentSearchDAO.insertRecentSearch(recentSearch)
        }
    }

    fun getAllAirport() : LiveData<List<Airport>> = airportDAO.getAllAirport()
    fun getAllRecentSearch() : LiveData<List<RecentSearch>> = recentSearchDAO.getAllRecentSearch()


    fun deleteAllRecentSearch() {
        TicketingDB.databaseWriteExecutor.execute {
            recentSearchDAO.deleteAllRecentSearch()
        }
    }

}