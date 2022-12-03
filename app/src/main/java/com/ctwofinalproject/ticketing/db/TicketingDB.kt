package com.ctwofinalproject.ticketing.db

import android.content.Context
import androidx.room.*
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ctwofinalproject.ticketing.daos.AirportDAO
import com.ctwofinalproject.ticketing.entity.Airport
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [Airport::class], version = 1)
abstract  class TicketingDB : RoomDatabase() {
    abstract fun airportDao(): AirportDAO

    companion object {
        private const val NUMBER_OF_THREADS = 4

        @Volatile
        private var INSTANCE: TicketingDB? = null
        val databaseWriteExecutor: ExecutorService =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS) // using for creating a database but not in main thread
//     that can interrupt our ui, so we have to create database in background

        //     that can interrupt our ui, so we have to create database in background
        fun getDatabase(context: Context): TicketingDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(TicketingDB::class.java) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TicketingDB::class.java,
                    "ticketing_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}