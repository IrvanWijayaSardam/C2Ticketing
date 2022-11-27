package com.ctwofinalproject.ticketing.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.ActivityMainHomeBinding

class MainActivityHome : AppCompatActivity() {
    lateinit var binding: ActivityMainHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}