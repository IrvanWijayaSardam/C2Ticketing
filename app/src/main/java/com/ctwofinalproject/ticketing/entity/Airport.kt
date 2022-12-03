package com.ctwofinalproject.ticketing.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
class Airport (
    @PrimaryKey()
    @ColumnInfo(name = "airportid")
    var id: Int = 0,
    @ColumnInfo(name = "airportName")
    var airportName: String? = null,
    @ColumnInfo(name = "airportCode")
    var airportCode: String? = null,
    @ColumnInfo(name = "city")
    var city: String? = null,
    @ColumnInfo(name = "country")
    var country : String? = null,
    ) : Parcelable