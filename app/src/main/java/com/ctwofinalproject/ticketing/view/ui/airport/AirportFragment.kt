package com.ctwofinalproject.ticketing.view.ui.airport

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentAirportBinding
import com.ctwofinalproject.ticketing.databinding.FragmentHomeBinding
import com.ctwofinalproject.ticketing.view.adapter.AirportAdapter
import com.ctwofinalproject.ticketing.viewmodel.AirportViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AirportFragment : Fragment() {
    private var _binding : FragmentAirportBinding?                         = null
    private val binding get()                                              = _binding!!
    lateinit var viewModelProto                                            : ProtoViewModel
    lateinit var viewModelAirport                                          : AirportViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAirportBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelProto                                      = ViewModelProvider(this).get(ProtoViewModel::class.java)
        viewModelAirport                                    = ViewModelProvider(this).get(AirportViewModel::class.java)
        viewModelProto.dataUser.observe(viewLifecycleOwner, {
            getAllAirport(it.token)
        })

    }

    private fun getAllAirport(token: String){
        viewModelAirport.fetchAirport("bearer "+token)
        viewModelAirport.getDataAirport().observe(viewLifecycleOwner, {
            Log.d(TAG, "getAllAirport: $it")
            if(it != null){
                binding.rvAirport.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvAirport.adapter = AirportAdapter(it)
                val adapter = AirportAdapter(it)
                adapter.notifyDataSetChanged()
            }
        })

    }


}