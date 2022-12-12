package com.ctwofinalproject.ticketing.view.ui.booking

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentAirportBinding
import com.ctwofinalproject.ticketing.databinding.FragmentShowTicketBinding
import com.ctwofinalproject.ticketing.model.DataItemFlight
import com.ctwofinalproject.ticketing.view.adapter.ShowTicketAdapter
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.ctwofinalproject.ticketing.viewmodel.ShowTicketViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowTicketFragment : Fragment() {
    private var _binding : FragmentShowTicketBinding?                         = null
    private val binding get()                                                 = _binding!!
    val viewModelShowticket                                                   : ShowTicketViewModel by viewModels()
    lateinit var sharedPref                                                   : SharedPreferences
    lateinit var editPref                                                     : SharedPreferences.Editor
    lateinit var adapterShowTicket                                            : ShowTicketAdapter
    lateinit var dialog                                                       : Dialog
    private val viewModelProto                                                : ProtoViewModel by viewModels()
    private var ticketIdDeparture                                             = ""
    private var ticketIdReturn                                                = ""
    private var airportDeparture                                              = ""
    private var airportReturn                                                 = ""
    private var dateDeparture                                                 = ""

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
        editPref                                            = sharedPref.edit()
        adapterShowTicket                                   = ShowTicketAdapter()
        initListener()
        setBottomNav()
        setDialog()

        Log.d(TAG, "onViewCreated: masuk show ticket fragment")
        airportDeparture = sharedPref.getString("airportCodeFrom","YIA").toString()
        airportReturn    = sharedPref.getString("airportCodeTo","YIA").toString()
        dateDeparture    = sharedPref.getString("departureDate","2022-09-30").toString()

        viewModelProto.dataBooking.observe(viewLifecycleOwner){
            Log.d(TAG, "onViewCreated: dataBooking ${it}")
            if(!sharedPref.getBoolean("isRoundTrip",false)){
                if(it.ticketIdDeparture.toString().isNullOrEmpty()) {
                    Log.d(TAG, "onViewCreated: roundtrip False , ticketIdDepartureIsNullOrEmpty")
                    binding.tvFromAirportCodeFragmentShowTicket.text = sharedPref.getString("airportCodeFrom","YIA").toString()
                    binding.tvToAirportCodeFragmentShowTicket.text = sharedPref.getString("airportCodeTo","YIA").toString()
                    binding.tvDayAndDateTicketFragmentShowTicket.text = sharedPref.getString("departureDate","2022-09-30").toString()
                    binding.tvCountTicketFragmentShowTicket.text = sharedPref.getInt("totalPassenger",1).toString()
                    searchFlight(sharedPref.getString("airportCodeFrom","YIA").toString(),sharedPref.getString("airportCodeTo","YIA").toString(),sharedPref.getString("departureDateForApi","2022-09-30").toString())
                } else {
                    Log.d(TAG, "onViewCreated: masukElse isnullorempty")
                    binding.tvFromAirportCodeFragmentShowTicket.text = sharedPref.getString("airportCodeFrom","YIA").toString()
                    binding.tvToAirportCodeFragmentShowTicket.text = sharedPref.getString("airportCodeTo","YIA").toString()
                    binding.tvDayAndDateTicketFragmentShowTicket.text = sharedPref.getString("departureDate","2022-09-30").toString()
                    binding.tvCountTicketFragmentShowTicket.text = sharedPref.getInt("totalPassenger",1).toString()
                    searchFlight(sharedPref.getString("airportCodeFrom","YIA").toString(),sharedPref.getString("airportCodeTo","YIA").toString(),sharedPref.getString("departureDateForApi","2022-09-30").toString())
                }
            } else {
                if(it.ticketIdDeparture.toString().isNullOrEmpty()){
                    Log.d(TAG, "onViewCreated: roundtrip true , ticketIdDepartureIsNullOrEmpty")
                    binding.tvFromAirportCodeFragmentShowTicket.text = sharedPref.getString("airportCodeFrom","YIA").toString()
                    binding.tvToAirportCodeFragmentShowTicket.text = sharedPref.getString("airportCodeTo","YIA").toString()
                    binding.tvDayAndDateTicketFragmentShowTicket.text = sharedPref.getString("departureDate","2022-09-30").toString()
                    binding.tvCountTicketFragmentShowTicket.text = sharedPref.getInt("totalPassenger",1).toString()
                    searchFlight(sharedPref.getString("airportCodeFrom","YIA").toString(),sharedPref.getString("airportCodeTo","YIA").toString(),sharedPref.getString("departureDateForApi","").toString())
                } else {
                    Log.d(TAG, "onViewCreated: roundtrip true , else")
                    binding.tvFromAirportCodeFragmentShowTicket.text = sharedPref.getString("airportCodeTo","YIA").toString()
                    binding.tvToAirportCodeFragmentShowTicket.text = sharedPref.getString("airportCodeFrom","YIA").toString()
                    binding.tvDayAndDateTicketFragmentShowTicket.text = sharedPref.getString("returnDate","2022-09-30").toString()
                    binding.tvCountTicketFragmentShowTicket.text = sharedPref.getInt("totalPassenger",1).toString()
                    binding.tvDepartureFlightFragmentShowTicket.text = "Return Flight"
                    searchFlightReturn(sharedPref.getString("airportCodeTo","YIA").toString(),sharedPref.getString("airportCodeFrom","YIA").toString(),sharedPref.getString("returnDateForApi","").toString())
                }
            }
        }

        viewModelShowticket.liveDataFlight.observe(viewLifecycleOwner) {
            if (it != null) {
                adapterShowTicket.submitList(it.data!!)
                binding.shimmerBar.visibility = View.GONE
                binding.rvShowTicketFragmentShowTicket.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvShowTicketFragmentShowTicket.adapter = adapterShowTicket
            } else {
                binding.rvShowTicketFragmentShowTicket.adapter = null
                binding.shimmerBar.visibility = View.GONE
                dialog.show()
            }
        }

        viewModelShowticket.liveDataFlightReturn.observe(viewLifecycleOwner){
            if (it != null) {
                adapterShowTicket.submitList(it.data!!)
                binding.shimmerBar.visibility = View.GONE
                binding.rvShowTicketFragmentShowTicket.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvShowTicketFragmentShowTicket.adapter = adapterShowTicket
            } else {
                binding.rvShowTicketFragmentShowTicket.adapter = null
                binding.shimmerBar.visibility = View.GONE
                dialog.show()
            }
        }

        adapterShowTicket.setOnItemClickListener(object : ShowTicketAdapter.onItemClickListener{
            override fun onItemClick(dataItemFlight: DataItemFlight) {
                if(!sharedPref.getBoolean("isRoundTrip",false)){
                    viewModelProto.submitTicketIdDeparture(dataItemFlight.id.toString())
                    ticketIdDeparture = dataItemFlight.id.toString()
                    gotoTripSummaryPasssenger()
                } else {
                    if (ticketIdDeparture.isNullOrEmpty()){
                        viewModelProto.submitTicketIdDeparture(dataItemFlight.id.toString())
                        ticketIdDeparture = dataItemFlight.id.toString()
                    } else {
                        viewModelProto.submitTicketIdReturn(dataItemFlight.id.toString())
                        ticketIdReturn = dataItemFlight.id.toString()
                        gotoTripSummaryPasssenger()
                    }
                }
            }
        })
    }

    private fun initListener() {
        binding?.run {
            tvFromAirportCodeFragmentShowTicket.text = airportDeparture
            tvToAirportCodeFragmentShowTicket.text = airportReturn
            tvDayAndDateTicketFragmentShowTicket.text = dateDeparture
            tvCountTicketFragmentShowTicket.text = sharedPref.getInt("totalPassenger",1).toString()

            ivGotoBackFromFragmentShowTicket.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }
        }
    }

    private fun searchFlight(departure: String, arrival: String, dateDeparture: String){
        viewModelShowticket.searchTicket(departure, arrival, dateDeparture)
    }

    private fun searchFlightReturn(departure: String,arrival: String, dateDeparture: String){
        viewModelShowticket.searchTicketReturn(departure, arrival, dateDeparture)
    }

    private fun setBottomNav(){
        val navBar                                     = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility                             = View.GONE
    }

    private fun setDialog(){
        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)

        val btnBack : Button = dialog.findViewById(R.id.btnBackCustomDialog)
        val btnClose : Button = dialog.findViewById(R.id.btnCloseCustomDialog)

        btnBack.setOnClickListener {
            dialog.dismiss()
            Navigation.findNavController(binding.root).popBackStack()
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
            Navigation.findNavController(binding.root).popBackStack()
        }
    }

    private fun gotoTripSummaryPasssenger(){
        Navigation.findNavController(requireView()).navigate(R.id.action_showTicketFragment_to_tripSummaryPassengerFragment)
    }
}