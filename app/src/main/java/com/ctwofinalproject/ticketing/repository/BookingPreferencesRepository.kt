package com.ctwofinalproject.ticketing.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.ctwofinalproject.ticketing.BookingDataProto
import com.ctwofinalproject.ticketing.proto.BookingDataPreferencesSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

private val Context.bookingPreferencesStore: DataStore<BookingDataProto> by dataStore(
    fileName = "bookingData",
    serializer = BookingDataPreferencesSerializer
)

class BookingPreferencesRepository(private val context: Context){

    suspend fun saveOneWay(ticketIdDeparture: String, passengerList: String, totalPrice: String){
        context.bookingPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setTicketIdDeparture(ticketIdDeparture).build()
        }
        context.bookingPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setPassengerList(passengerList).build()
        }
        context.bookingPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setTotalPrice(totalPrice).build()
        }
    }

    suspend fun saveTwoWay(ticketIdDeparture: String,ticketIdReturn: String, passengerList: String, totalPrice: String){
        context.bookingPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setTicketIdDeparture(ticketIdDeparture).build()
        }
        context.bookingPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setTicketIdReturn(ticketIdReturn).build()
        }
        context.bookingPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setPassengerList(passengerList).build()
        }
        context.bookingPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setTotalPrice(totalPrice).build()
        }
    }

    suspend fun setTicketIdDeparture(ticketIdDeparture: String){
        context.bookingPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setTicketIdDeparture(ticketIdDeparture).build()
        }
    }


    suspend fun setTicketIdReturn(ticketIdReturn: String){
        context.bookingPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setTicketIdReturn(ticketIdReturn).build()
        }
    }

    suspend fun deleteTicketIdDeparture(){
        context.bookingPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearTicketIdDeparture().build()
        }
    }

    suspend fun deleteTicketIdReturn(){
        context.bookingPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearTicketIdReturn().build()
        }
    }

    suspend fun clearData(){
        context.bookingPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearTicketIdDeparture().build()
        }
        context.bookingPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearTicketIdReturn().build()
        }
        context.bookingPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearPassengerList().build()
        }
        context.bookingPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearTotalPrice().build()
        }
    }

    val readProto: Flow<BookingDataProto> = context.bookingPreferencesStore.data
        .catch { exception ->
            if(exception is IOException){
                Log.d(TAG, "Error reading sort order preferences.",exception)
                emit(BookingDataProto.getDefaultInstance())
            } else {
                throw exception
            }
        }

}