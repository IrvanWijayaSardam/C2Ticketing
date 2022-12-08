package com.ctwofinalproject.ticketing.view.ui.booking

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.data.Passanger
import com.ctwofinalproject.ticketing.databinding.FragmentAddPassengerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddPassengerFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddPassengerBinding?                 = null
    private val binding get()                                          = _binding!!
    private lateinit var listener                                      : onItemClickListener
    private var passengerData                                          : Passanger? = null
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
            btnSaveAddPassenger.setOnClickListener {
                listener.onItemClick(Passanger(edtAgeAddPassenger.text.toString().toInt(),edtEmailAddPassenger.text.toString(),edtFirstNameAddPassenger.text.toString(),edtIdentityNumberAddPassenger.text.toString(),spIdentityType.selectedItem.toString(),edtLastNameAddPassenger.text.toString()))
                clearAll()
                dismiss()
            }
        }
    }


    interface onItemClickListener {
        fun onItemClick(passenger: Passanger){
        }
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        this.listener = listener
    }

    fun getPassenger(passenger: Passanger){
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
        binding.edtFirstNameAddPassenger.setText(passengerData!!.firstname.toString())
        binding.edtLastNameAddPassenger.setText(passengerData!!.lastname.toString())
        binding.edtEmailAddPassenger.setText(passengerData!!.email.toString())
        binding.edtAgeAddPassenger.setText(passengerData!!.age.toString())
        binding.edtIdentityNumberAddPassenger.setText(passengerData!!.identityNumber.toString())
    }

    fun clearAll(){
        binding.edtFirstNameAddPassenger.setText("")
        binding.edtLastNameAddPassenger.setText("")
        binding.edtEmailAddPassenger.setText("")
        binding.edtAgeAddPassenger.setText("")
        binding.edtIdentityNumberAddPassenger.setText("")

    }
}