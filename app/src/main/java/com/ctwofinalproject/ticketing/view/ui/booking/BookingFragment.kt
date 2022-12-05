package com.ctwofinalproject.ticketing.view.ui.booking

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentBookingBinding
import com.ctwofinalproject.ticketing.databinding.FragmentLoginBinding
import com.ctwofinalproject.ticketing.entity.RecentSearch
import com.ctwofinalproject.ticketing.viewmodel.BookingViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class BookingFragment : Fragment() {
    private var _binding: FragmentBookingBinding?                 = null
    private val binding get()                                     = _binding!!
    lateinit var sharedPref                                       : SharedPreferences
    val bookingViewModel                                          : BookingViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBookingBinding.inflate(inflater,container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref                                          = requireContext().getSharedPreferences("sharedairport", Context.MODE_PRIVATE)
        initListener()
    }

    private fun initListener() {
        binding?.run {
            cvOneWaySearchFlightFragmentBooking.setCardBackgroundColor(resources.getColor(R.color.secondary_font_color))
            cvRoundTripSearchFlightFragmentBooking.setCardBackgroundColor(resources.getColor(R.color.primary_blue_1))

            btnSearchTicketFragmentBooking.setOnClickListener {
                bookingViewModel.insertRecentSearch(RecentSearch(0,tvFromAirportCodeFragmentBooking.text.toString(),tvToAirportCodeFragmentBooking.text.toString(),"Economy",sharedPref.getString("departureDate","Day,xx Month xxxx")
                    ,sharedPref.getString("returnDate","Day, xx Mont xxxx")))
                Navigation.findNavController(requireView()).navigate(R.id.action_bookingFragment_to_addPassengerFragment)
            }

            cvOneWaySearchFlightFragmentBooking.setOnClickListener {
//            switch to framgent SelectOneWayFragment
                cvOneWaySearchFlightFragmentBooking.setCardBackgroundColor(resources.getColor(R.color.primary_blue_1))
                cvRoundTripSearchFlightFragmentBooking.setCardBackgroundColor(resources.getColor(R.color.secondary_font_color))
                val selectOneWayFragment = SelectOneWayFragment()
                val manager = activity?.supportFragmentManager
                val transaction = manager?.beginTransaction()
                transaction?.replace(R.id.fragmentContainerViewDepartureAndREturnDateFragmentBooking, selectOneWayFragment)
                transaction?.addToBackStack(null)
                transaction?.commit()
            }
            cvRoundTripSearchFlightFragmentBooking.setOnClickListener {
                cvRoundTripSearchFlightFragmentBooking.setCardBackgroundColor(resources.getColor(R.color.primary_blue_1))
                cvOneWaySearchFlightFragmentBooking.setCardBackgroundColor(resources.getColor(R.color.secondary_font_color))
                //            switch to framgent SelectRoundTripFragment
                val selectRoundTripFragment = SelectRoundTripFragment()
                val manager = activity?.supportFragmentManager
                val transaction = manager?.beginTransaction()
                transaction?.replace(R.id.fragmentContainerViewDepartureAndREturnDateFragmentBooking, selectRoundTripFragment)
                transaction?.addToBackStack(null)
                transaction?.commit()
            }

            llBookFromFragmentBooking.setOnClickListener {
                gotoSelectAirport("from","booking")
            }
            llBookToFragmentBooking.setOnClickListener {
                gotoSelectAirport("to","booking")
            }

            tvFromAirportCodeFragmentBooking.text = sharedPref.getString("airportCodeFrom","YIA")
            tvFromAirportNameFragmentBooking.text = sharedPref.getString("airportNameFrom","AirportName")

            tvToAirportCodeFragmentBooking.text   = sharedPref.getString("airportCodeTo","Select")
            tvToAirportNameFragmentBooking.text   = sharedPref.getString("airportNameTo","Airport Name")

        }
    }

    fun gotoSelectAirport(fromto : String, fFragment : String){
        var bund = Bundle()
        bund.putString("fromto",fromto)
        bund.putString("fromFragment",fFragment)
        Navigation.findNavController(requireView()).navigate(R.id.action_bookingFragment_to_airportFragment,bund)
    }
}