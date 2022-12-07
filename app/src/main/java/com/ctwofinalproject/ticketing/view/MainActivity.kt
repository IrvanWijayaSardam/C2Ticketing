package com.ctwofinalproject.ticketing.view

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.ActivityMainBinding
import com.ctwofinalproject.ticketing.view.adapter.ShowTicketAdapter
import com.ctwofinalproject.ticketing.view.ui.home.HomeFragment
import com.ctwofinalproject.ticketing.view.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding                                                      : ActivityMainBinding
    lateinit var sharedPref                                                   : SharedPreferences
    lateinit var editPref                                                     : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref                                          = this.getSharedPreferences("sharedairport", Context.MODE_PRIVATE)
        editPref                                            = sharedPref.edit()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Masuk OnDestroy Main")
        editPref.clear()
        editPref.apply()
    }


}