package com.ctwofinalproject.ticketing.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.ActivityMainHomeBinding
import com.ctwofinalproject.ticketing.view.ui.home.HomeFragment
import com.ctwofinalproject.ticketing.view.ui.profile.ProfileFragment

class MainActivityHome : AppCompatActivity() {
    lateinit var binding : ActivityMainHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

//        binding.bottomNav.visibility = View.GONE

        binding.bottomNav.setOnItemReselectedListener{

            when(it.itemId){

                R.id.iconHomeBN -> replaceFragment(HomeFragment())
                R.id.iconProfileBN -> replaceFragment(ProfileFragment())

            else -> {

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}