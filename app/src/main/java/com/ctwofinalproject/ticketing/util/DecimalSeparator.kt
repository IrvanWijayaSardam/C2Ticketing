package com.ctwofinalproject.ticketing.util

object DecimalSeparator {
    fun formatDecimalSeperators(number :String) :String {
        var index = 1
        return number
            .takeIf { it.length > 3 }
            ?.reversed()
            ?.map { if (index++ % 3 == 0) "$it," else it }
            ?.joinToString("")
            ?.reversed()
            ?.removePrefix(",")
            ?: number
    }
}