package com.ctwofinalproject.ticketing.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.ActivityMainBinding
import com.ctwofinalproject.ticketing.view.ui.home.HomeFragment
import com.ctwofinalproject.ticketing.view.ui.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemReselectedListener {
            when(it.itemId){


                else -> {

                }
            }
            true
        }
    }
}