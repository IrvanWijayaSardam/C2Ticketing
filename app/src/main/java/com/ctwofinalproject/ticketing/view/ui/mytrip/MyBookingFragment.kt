package com.ctwofinalproject.ticketing.view.ui.mytrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentHomeBinding
import com.ctwofinalproject.ticketing.databinding.FragmentMyBookingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyBookingFragment : Fragment() {
    private var _binding : FragmentMyBookingBinding?                         = null
    private val binding get()                                                = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyBookingBinding.inflate(inflater,container,false)
        return binding?.root
    }


}