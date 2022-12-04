package com.ctwofinalproject.ticketing.view.ui.booking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentAddPassengerBinding
import com.ctwofinalproject.ticketing.databinding.FragmentLoginBinding


class AddPassengerFragment : Fragment() {

    private var _binding: FragmentAddPassengerBinding?                 = null
    private val binding get()                                   = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPassengerBinding.inflate(inflater,container, false)
        return binding?.root
    }


}