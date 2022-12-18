package com.ctwofinalproject.ticketing.view.ui.booking

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentAirportBinding
import com.ctwofinalproject.ticketing.databinding.FragmentSelectOneWayBinding
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SelectOneWayFragment : Fragment() {
    private var _binding : FragmentSelectOneWayBinding?                         = null
    private val binding get()                                                   = _binding!!
    lateinit var sharedPref                                                     : SharedPreferences
    lateinit var editPref                                                       : SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectOneWayBinding.inflate(inflater,container,false)
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
            llDepatrtureDateFragmentOneWay.setOnClickListener {
                val datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("CHOOSE DEPARTURE DATE")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
                datePicker.show(
                    this@SelectOneWayFragment.requireActivity().supportFragmentManager,
                    "datePicker"
                )
                datePicker.addOnPositiveButtonClickListener {
                    val depatureFormat = SimpleDateFormat("EEE, MMM d, ''yyyy", Locale.getDefault())
                    val depatureFormatForApi = SimpleDateFormat("YYYY-MM-dd")

                    val depatureDate = depatureFormat.format(Date(it).time)
                    val depatureDateForApi = depatureFormatForApi.format(Date(it).time)
                    editPref.putString("departureDate",depatureDate)
                    editPref.putString("departureDateForApi",depatureDateForApi)
                    editPref.apply()
                    tvDateDepartureDateFragmentSelectOneWay.setText(depatureDate)
                }
            }
            tvDateDepartureDateFragmentSelectOneWay.text = sharedPref.getString("departureDate","Day,xx Month xxxx")
        }
    }

}