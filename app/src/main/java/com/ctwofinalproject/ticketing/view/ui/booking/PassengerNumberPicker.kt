package com.ctwofinalproject.ticketing.view.ui.booking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.data.Passenger
import com.ctwofinalproject.ticketing.databinding.FragmentAddPassengerBinding
import com.ctwofinalproject.ticketing.databinding.FragmentPassengerNumberPickerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PassengerNumberPicker : BottomSheetDialogFragment() {
    private var _binding: FragmentPassengerNumberPickerBinding?                 = null
    private val binding get()                                                   = _binding!!
    private lateinit var listener                                               : onItemClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPassengerNumberPickerBinding.inflate(inflater,container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding?.run {
            binding.npPassenger.maxValue = 10
            binding.npPassenger.minValue = 1
            btnNp.setOnClickListener {
                listener.onItemClick(binding?.npPassenger!!.value)
                dismiss()
            }
        }
    }

    interface onItemClickListener {
        fun onItemClick(totalPassenger : Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        this.listener = listener
    }

}