package com.ctwofinalproject.ticketing.util

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar

object ShowSnack {
    fun show(view: View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}