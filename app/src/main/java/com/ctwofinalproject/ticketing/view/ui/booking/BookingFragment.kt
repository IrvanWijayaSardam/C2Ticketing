package com.ctwofinalproject.ticketing.view.ui.booking

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentBookingBinding
import com.ctwofinalproject.ticketing.databinding.FragmentLoginBinding
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class BookingFragment : Fragment() {
    private var _binding: FragmentBookingBinding?                 = null
    private val binding get()                                     = _binding!!
    lateinit var sharedPref                                       : SharedPreferences

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
            /*
            llDateDepartureDateFragmentBooking.setOnClickListener {
                val datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("CHOOSE BIRTHDAY DATE")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
                datePicker.show(
                    this@BookingFragment.requireActivity().supportFragmentManager,
                    "datePicker"
                )
                datePicker.addOnPositiveButtonClickListener {
                    val depatureFormat = SimpleDateFormat("EEE, MMM d, ''yyyy", Locale.getDefault())
                    val depatureDate = depatureFormat.format(Date(it).time)
                    tvDateDepartureDateFragmentBooking.setText(depatureDate)
                }
            }

             */
            tvFromAirportCodeFragmentBooking.text = sharedPref.getString("airportCodeFrom","YIA")
            tvFromAirportNameFragmentBooking.text = sharedPref.getString("airportNameFrom","AirportName")

            tvToAirportCodeFragmentBooking.text   = sharedPref.getString("airportCodeTo","Select")
            tvToAirportNameFragmentBooking.text   = sharedPref.getString("airportNameTo","Airport Name")

        }

    }
}