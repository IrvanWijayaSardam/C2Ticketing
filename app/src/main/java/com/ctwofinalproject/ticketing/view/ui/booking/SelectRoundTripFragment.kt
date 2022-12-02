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
import com.ctwofinalproject.ticketing.databinding.FragmentSelectRoundTripBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*


class SelectRoundTripFragment : Fragment() {
    private var _binding: FragmentSelectRoundTripBinding?                       = null
    private val binding get()                                                   = _binding!!
    lateinit var sharedPref                                                     : SharedPreferences
    lateinit var editPref                                                       : SharedPreferences.Editor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSelectRoundTripBinding.inflate(inflater,container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref                                          = requireContext().getSharedPreferences("sharedairport", Context.MODE_PRIVATE)
        editPref                                            = sharedPref.edit()

        initListener()
    }

    private fun initListener() {
        binding?.run {
            llReturnDateFragmentRoundTrip.setOnClickListener {
                val datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("CHOOSE BIRTHDAY DATE")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
                datePicker.show(
                    this@SelectRoundTripFragment.requireActivity().supportFragmentManager,
                    "datePicker"
                )
                datePicker.addOnPositiveButtonClickListener {
                    val returnFormat = SimpleDateFormat("EEE, MMM d, ''yyyy", Locale.getDefault())
                    val returnDate = returnFormat.format(Date(it).time)
                    editPref.putString("returnDate",returnDate)
                    editPref.apply()
                    tvDateReturnDateFragmentRoundTrip.setText(returnDate)
                }
            }
            tvDateReturnDateFragmentRoundTrip.text = sharedPref.getString("returnDate","Day, xx Mont xxxx")
            tvDateDepartureDateFragmentRoundTrip.text = sharedPref.getString("departureDate","Day,xx Month xxxx")
        }
    }

}