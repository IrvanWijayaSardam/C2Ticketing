package com.ctwofinalproject.ticketing.view.ui.airport

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentAirportBinding
import com.ctwofinalproject.ticketing.model.DataItem
import com.ctwofinalproject.ticketing.view.adapter.AirportAdapter
import com.ctwofinalproject.ticketing.viewmodel.AirportViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class AirportFragment : Fragment() {
    private var _binding : FragmentAirportBinding?                         = null
    private val binding get()                                              = _binding!!
    val viewModelProto                                                     : ProtoViewModel by viewModels()
    val viewModelAirport                                                   : AirportViewModel by viewModels()
    lateinit var fromto                                                    : String
    lateinit var fFragment                                                 : String
    lateinit var adapter                                                   : AirportAdapter
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
        adapter                                             = AirportAdapter()
        getArgs()
        initListener()

        viewModelProto.dataUser.observe(viewLifecycleOwner, {
            getAllAirport(it.token)
            token = it.token
        })

        viewModelAirport.getDataAirport().observe(viewLifecycleOwner, {
            Log.d(TAG, "getAllAirport: $it")
            if(it != null){
                adapter.submitList(it.data)
                binding.shimmerBar.visibility = View.GONE
                binding.rvAirport.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvAirport.adapter = adapter
            }
        })

        viewModelAirport.getDataAirportSearch().observe(viewLifecycleOwner, {
            Log.d(TAG, "getAllAirport: $it")
            if(it != null){
                adapter.submitList(it.data)
                binding.shimmerBar.visibility = View.GONE
                binding.rvAirport.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvAirport.adapter = adapter
            }
        })

        
        adapter.setOnItemClickListener(object : AirportAdapter.onItemClickListener{
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
        }
    }


    private fun getAllAirport(token: String){
        viewModelAirport.fetchAirport("bearer "+token)
    }

    private fun getArgs() {
        fromto = arguments?.getString("fromto").toString()
        fFragment = arguments?.getString("fromFragment").toString()
    }

}