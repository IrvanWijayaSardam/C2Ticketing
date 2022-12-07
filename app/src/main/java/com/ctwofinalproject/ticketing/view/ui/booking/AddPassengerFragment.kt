package com.ctwofinalproject.ticketing.view.ui.booking

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.data.Passenger
import com.ctwofinalproject.ticketing.databinding.FragmentAddPassengerBinding
import com.ctwofinalproject.ticketing.databinding.FragmentLoginBinding
import com.ctwofinalproject.ticketing.model.DataItem
import com.ctwofinalproject.ticketing.view.adapter.AirportAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddPassengerFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddPassengerBinding?                 = null
    private val binding get()                                          = _binding!!
    private lateinit var listener                                      : onItemClickListener
    private var passengerData                                          : Passenger? = null
    lateinit var sharedPref                                            : SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPassengerBinding.inflate(inflater,container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref                                          = requireContext().getSharedPreferences("sharedairport", Context.MODE_PRIVATE)

        initListener()
    }

    private fun initListener() {
        binding?.run {
            btnSavePassenger.setOnClickListener {
                listener.onItemClick(Passenger(aCtCountrySignUp.text.toString(),tIetFirstnamePassenger.text.toString(),tIetLastnamePassenger.text.toString(),tIetNikPassenger.text.toString()))
                dismiss()
            }
        }
    }

    interface onItemClickListener {
        fun onItemClick(passenger: Passenger)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        this.listener = listener
    }

    fun getPassenger(passenger: Passenger){
        passengerData = passenger
    }

    override fun onResume() {
        if (passengerData != null){
            setData()
        }
        super.onResume()
    }

    override fun onPause() {
        passengerData = null
        super.onPause()
    }

    fun setData(){
        binding.aCtCountrySignUp.setText(passengerData!!.title.toString())
        binding.tIetFirstnamePassenger.setText(passengerData!!.firstname.toString())
        binding.tIetLastnamePassenger.setText(passengerData!!.lastname.toString())
        binding.tIetNikPassenger.setText(passengerData!!.nik.toString())
    }
}