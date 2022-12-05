package com.ctwofinalproject.ticketing.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
class RecentSearch (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "airportCodeFrom")
    var airportCodeFrom: String? = null,
    @ColumnInfo(name = "airportCodeTo")
    var airportCodeTo: String? = null,
    @ColumnInfo(name = "flightClass")
    var flightClass: String? = null,
    @ColumnInfo(name = "depatureDate")
    var depatureDate: String? = null,
    @ColumnInfo(name = "returnDate")
    var returnDate: String? = null,
) : Parcelable