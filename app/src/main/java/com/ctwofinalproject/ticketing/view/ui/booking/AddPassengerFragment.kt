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
        setAdapter()
    }

    private fun initListener() {
        binding?.run {
            btnSavePassenger.setOnClickListener {
                listener.onItemClick(Passanger(tIetAgePassenger.text.toString().toInt(),tIetEmailPassenger.text.toString(),tIetFirstnamePassenger.text.toString(),tIetNikPassenger.text.toString(),aCtDocumentTypeAddPassenger.text.toString(),tIetLastnamePassenger.text.toString()))
                dismiss()
            }
        }
    }

    private fun setAdapter(){
        val title                                           = resources.getStringArray(R.array.title)
        val documentType                                    = resources.getStringArray(R.array.documentTypePassenger)
        val arrayAdapterTitle                               = ArrayAdapter(requireContext(),R.layout.drop_down_item,title)
        val arrayAdapterDocumentType                        = ArrayAdapter(requireContext(),R.layout.drop_down_item,documentType)
        binding.aCtTitleAddPassenger.setAdapter(arrayAdapterTitle)
        binding.aCtDocumentTypeAddPassenger.setAdapter(arrayAdapterDocumentType)
    }

    interface onItemClickListener {
        fun onItemClick(passenger: Passanger)
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
        binding.tIetFirstnamePassenger.setText(passengerData!!.firstname.toString())
        binding.tIetLastnamePassenger.setText(passengerData!!.firstname.toString())
        binding.tIetNikPassenger.setText(passengerData!!.identityNumber.toString())
    }
}