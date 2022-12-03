package com.ctwofinalproject.ticketing.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ctwofinalproject.ticketing.entity.Airport

@Dao
interface AirportDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAirport(airport: Airport)

    @Query("SELECT * FROM Airport")
    fun getAllAirport() : LiveData<List<Airport>>
}