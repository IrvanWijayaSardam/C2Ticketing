package com.ctwofinalproject.ticketing.view.ui.mytrip

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentHomeBinding
import com.ctwofinalproject.ticketing.databinding.FragmentMyBookingBinding
import com.ctwofinalproject.ticketing.model.DataItemGetBooking
import com.ctwofinalproject.ticketing.view.adapter.MyBookingAdapter
import com.ctwofinalproject.ticketing.viewmodel.MyBookingViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyBookingFragment : Fragment() {
    private var _binding : FragmentMyBookingBinding?                         = null
    private val binding get()                                                = _binding!!
    private val viewModelMyBooking                                           : MyBookingViewModel by viewModels()
    val viewModelProto                                                       : ProtoViewModel by viewModels()
    private var token                                                        = ""
    lateinit var adapterMyBooking                                            : MyBookingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyBookingBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterMyBooking                                    = MyBookingAdapter()
        setBottomNav()

        viewModelProto.dataUser.observe(viewLifecycleOwner){
            if(it.isLogin){
                token = it.token
                viewModelMyBooking.getBooking("bearer "+token)
            }
        }

        viewModelMyBooking.liveDataResponseGetBooking.observe(viewLifecycleOwner){
            Log.d(TAG, "onViewCreated: livedataGetBooking ${it}")
            if(it != null){
                adapterMyBooking.submitList(it.data)
                binding.rvMyBooking.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                binding.rvMyBooking.adapter = adapterMyBooking
            }
        }
        adapterMyBooking.setOnItemClickListener(object : MyBookingAdapter.onItemClickListener{
            override fun onItemClick(dataItemGetBooking: DataItemGetBooking) {
                Log.d(TAG, "onItemClick: Belum tak Implementasi")
                val bund = Bundle()
                bund.putParcelable("dataItemGetBooking",dataItemGetBooking)
                Navigation.findNavController(requireView()).navigate(R.id.action_myTripFragment_to_paymentFragment,bund)
            }
        })
    }
    private fun setBottomNav(){
        val navBar                                     = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility                             = View.VISIBLE
    }
}