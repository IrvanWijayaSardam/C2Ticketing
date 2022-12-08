package com.ctwofinalproject.ticketing.util

import android.content.Context
import android.util.Log
import com.ctwofinalproject.ticketing.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {
    fun convertISOTime(context: Context, isoTime: String?): String {

        Log.d("Date", "convertISOTime: $isoTime")

        val sdf = SimpleDateFormat(context.getString(R.string.default_time_format), Locale.US)
        val convertedDate: Date?
        var formattedDate: String? = null
        var formattedTime = "10:00 AM"
        try {
            convertedDate = isoTime?.let { sdf.parse(it) }
            formattedDate = convertedDate?.let {
                SimpleDateFormat(context.getString(R.string.date_format)).format(
                    it
                )
            }
            formattedTime = convertedDate?.let {
                SimpleDateFormat(context.getString(R.string.time_format)).format(
                    it
                )
            }.toString()

//            Log.e("Time", formattedTime.toString())

            if((formattedTime.subSequence(6,8).toString() == "PM" || formattedTime.subSequence(6,8).toString() == "pm") && formattedTime.subSequence(0,2).toString().toInt()>12){
                formattedTime = (formattedTime.subSequence(0,2).toString().toInt()-12).toString()+formattedTime.subSequence(2,8).toString()
            }
            if (formattedTime.subSequence(0,2).toString() == "00"){
                formattedTime = (formattedTime.subSequence(0,2).toString().toInt()+1).toString()+formattedTime.subSequence(2,8).toString()

            }
            if (formattedTime.subSequence(0,2).toString() == "0:"){
                formattedTime = (formattedTime.subSequence(0,1).toString().toInt()+1).toString()+formattedTime.subSequence(2,8).toString()

            }


//            Log.d("Date ", "$formattedDate | $formattedTime")
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.d("Date", "convertISOTime: $formattedDate")
            Log.e("Error Date ", e.message!!)
        }

        return "$formattedDate"
    }
}