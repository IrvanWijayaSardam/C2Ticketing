package com.ctwofinalproject.ticketing.view.ui.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentHomeBinding
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding?             = null
    private val binding get()                               = _binding!!
    lateinit var viewModelProto                              : ProtoViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelProto                                      = ViewModelProvider(this).get(ProtoViewModel::class.java)
        
        viewModelProto.dataUser.observe(viewLifecycleOwner, {
            Log.d(TAG, "onViewCreated: ${it}")
            when{
                it == null -> binding.tvUsernameOrLogin.text = "Login"
                it.firstname != null -> binding.tvUsernameOrLogin.text = it.firstname
                else -> binding.tvUsernameOrLogin.text = "Login"
            }
        })
        
    }
}