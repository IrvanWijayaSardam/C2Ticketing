package com.ctwofinalproject.ticketing.view.ui.booking

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
import com.ctwofinalproject.ticketing.databinding.FragmentShowTicketBinding
import com.ctwofinalproject.ticketing.view.adapter.ShowTicketAdapter
import com.ctwofinalproject.ticketing.viewmodel.ShowTicketViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowTicketFragment : Fragment() {
    private var _binding : FragmentShowTicketBinding?                         = null
    private val binding get()                                                 = _binding!!
    val viewModelShowticket                                                   : ShowTicketViewModel by viewModels()
    lateinit var sharedPref                                                   : SharedPreferences
    lateinit var adapterShowTicket                                            : ShowTicketAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowTicketBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref                                          = requireContext().getSharedPreferences("sharedairport", Context.MODE_PRIVATE)
        adapterShowTicket                                   = ShowTicketAdapter()
        initListener()
        setBottomNav()
        searchFlight(sharedPref.getString("airportCodeFrom","YIA").toString(),sharedPref.getString("airportCodeTo","YIA").toString(),sharedPref.getString("departureDateForApi","2022-09-30").toString())

        viewModelShowticket.liveDataFlight.observe(viewLifecycleOwner, {
            if(it != null){
                adapterShowTicket.submitList(it.data!!)
                binding.shimmerBar.visibility = View.GONE
                binding.rvShowTicketFragmentShowTicket.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                binding.rvShowTicketFragmentShowTicket.adapter = adapterShowTicket
            }
        })
    }

    private fun initListener() {
        binding?.run {
            tvFromAirportCodeFragmentShowTicket.text = sharedPref.getString("airportCodeFrom","YIA").toString()
            tvToAirportCodeFragmentShowTicket.text = sharedPref.getString("airportCodeTo","YIA").toString()

            ivGotoBackFromFragmentShowTicket.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }
        }
    }

    private fun searchFlight(departure: String, arrival: String, dateDeparture: String){
        viewModelShowticket.searchTicket(departure, arrival, dateDeparture)
    }

    private fun setBottomNav(){
        val navBar                                     = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility                             = View.GONE
    }


}