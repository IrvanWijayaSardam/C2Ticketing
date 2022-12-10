package com.ctwofinalproject.ticketing.view.ui.airport

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentAirportBinding
import com.ctwofinalproject.ticketing.entity.Airport
import com.ctwofinalproject.ticketing.model.DataItem
import com.ctwofinalproject.ticketing.view.adapter.AirportAdapter
import com.ctwofinalproject.ticketing.view.adapter.RecentAirportAdapter
import com.ctwofinalproject.ticketing.viewmodel.AirportViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AirportFragment : Fragment() {
    private var _binding : FragmentAirportBinding?                         = null
    private val binding get()                                              = _binding!!
    val viewModelProto                                                     : ProtoViewModel by viewModels()
    val viewModelAirport                                                   : AirportViewModel by viewModels()
    lateinit var fromto                                                    : String
    lateinit var fFragment                                                 : String
    lateinit var adapterAirport                                            : AirportAdapter
    lateinit var adapterRecentAirport                                      : RecentAirportAdapter
    lateinit var sharedPref                                                : SharedPreferences
    lateinit var editPref                                                  : SharedPreferences.Editor
    lateinit var token                                                     : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAirportBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fromto                                              = ""
        fFragment                                           = ""
        sharedPref                                          = requireContext().getSharedPreferences("sharedairport", Context.MODE_PRIVATE)
        editPref                                            = sharedPref.edit()
        adapterAirport                                      = AirportAdapter()
        adapterRecentAirport                                = RecentAirportAdapter()

        getArgs()
        initListener()
        setBottomNav()

        viewModelProto.dataUser.observe(viewLifecycleOwner) {
            getAllAirport()
            if (it.isLogin) {
                token = it.token
            }
        }

        viewModelAirport.getAllAirport().observe(viewLifecycleOwner) {
            if (it != null) {
                adapterRecentAirport.submitList(it)
                binding.rvRecentAirport.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvRecentAirport.adapter = adapterRecentAirport
            } else {
            }
        }

        viewModelAirport.getDataAirport().observe(viewLifecycleOwner) {
            Log.d(TAG, "getAllAirport: $it")
            if (it != null) {
                adapterAirport.submitList(it.data)
                binding.shimmerBar.visibility = View.GONE
                binding.rvAirport.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvAirport.adapter = adapterAirport
            }
        }

        viewModelAirport.getDataAirportSearch().observe(viewLifecycleOwner) {
            Log.d(TAG, "getAllAirport: $it")
            if (it != null) {
                adapterAirport.submitList(it.data)
                binding.shimmerBar.visibility = View.GONE
                binding.rvAirport.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvAirport.adapter = adapterAirport
            }
        }


        adapterAirport.setOnItemClickListener(object : AirportAdapter.onItemClickListener{
            override fun onItemClick(airportData: DataItem) {
                when(fromto) {
                    "from" -> {
                        Log.d(TAG, "onItemClick: ${airportData.city}")
                        editPref.putInt("airportIdFrom", airportData.id!!)
                        editPref.putString("airportNameFrom",airportData.name)
                        editPref.putString("airportCodeFrom",airportData.code)
                    }
                    "to" -> {
                        Log.d(TAG, "onItemClick: ${airportData.city}")
                        editPref.putInt("airportIdTo", airportData.id!!)
                        editPref.putString("airportNameTo",airportData.name)
                        editPref.putString("airportCodeTo",airportData.code)
                    }
                }
                viewModelAirport.insert(Airport(airportData.id!!.toInt(),airportData.name.toString(),airportData.code.toString(),airportData.city.toString(),airportData.country.toString()))
                editPref.apply()
                when(fFragment){
                    "home" -> Navigation.findNavController(requireView()).navigate(R.id.action_airportFragment_to_homeFragment)
                    "booking" -> Navigation.findNavController(requireView()).navigate(R.id.action_airportFragment_to_bookingFragment)
                }
            }
        })

        adapterRecentAirport.setOnItemClickListener(object : RecentAirportAdapter.OnItemClickListener{
            override fun onItemClick(airport: Airport) {
                when(fromto) {
                    "from" -> {
                        Log.d(TAG, "onItemClick: ${airport.city}")
                        editPref.putInt("airportIdFrom", airport.id!!)
                        editPref.putString("airportNameFrom",airport.airportName)
                        editPref.putString("airportCodeFrom",airport.airportCode)
                    }
                    "to" -> {
                        Log.d(TAG, "onItemClick: ${airport.city}")
                        editPref.putInt("airportIdTo", airport.id!!)
                        editPref.putString("airportNameTo",airport.airportName)
                        editPref.putString("airportCodeTo",airport.airportCode)
                    }
                }
                editPref.apply()
                when(fFragment){
                    "home" -> Navigation.findNavController(requireView()).navigate(R.id.action_airportFragment_to_homeFragment)
                    "booking" -> Navigation.findNavController(requireView()).navigate(R.id.action_airportFragment_to_bookingFragment)
                }
            }
        })

    }

    private fun initListener(){
        binding?.run {
            ivSearch.setOnClickListener {
                viewModelAirport.searchAirport("bearer "+token, tIetSearchAirportFragmentAirport.text.toString())
            }
            ivBackFromChooseAirportFragmentAirport.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }
        }
    }


    private fun getAllAirport(){
        viewModelAirport.fetchAirport()
    }

    private fun getArgs() {
        fromto = arguments?.getString("fromto").toString()
        fFragment = arguments?.getString("fromFragment").toString()
    }

    private fun setBottomNav(){
        val navBar                                     = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility                             = View.GONE
    }


}