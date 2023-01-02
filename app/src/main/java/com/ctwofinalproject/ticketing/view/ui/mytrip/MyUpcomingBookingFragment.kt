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
import com.ctwofinalproject.ticketing.databinding.FragmentMyUpcomingBookingBinding
import com.ctwofinalproject.ticketing.model.DataItemHistory
import com.ctwofinalproject.ticketing.view.adapter.MyBookingUpcomingAdapter
import com.ctwofinalproject.ticketing.viewmodel.MyBookingViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyUpcomingBookingFragment : Fragment() {
    private var _binding : FragmentMyUpcomingBookingBinding?                         = null
    private val binding get()                                                        = _binding!!
    private val viewModelProto                                                       : ProtoViewModel by viewModels()
    private val viewModelMyBookingViewModel                                          : MyBookingViewModel by viewModels()
    private var token                                                                = ""
    lateinit var adapterMyUpcomingBooking                                            : MyBookingUpcomingAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyUpcomingBookingBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterMyUpcomingBooking                        = MyBookingUpcomingAdapter()
        viewModelProto.dataUser.observe(viewLifecycleOwner){
            if (it.isLogin){
                token = it.token
                viewModelMyBookingViewModel.getHistory("1","bearer "+token)
            } else {
                Log.d(TAG, "onViewCreated: need to be loggedin ")
            }
        }

        viewModelMyBookingViewModel.liveDataResponseGetHistory.observe(viewLifecycleOwner){
            if(it != null){
                adapterMyUpcomingBooking.submitList(it.data)
                binding.rvMyBookingUpcoming.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                binding.rvMyBookingUpcoming.adapter = adapterMyUpcomingBooking
                binding.ivEmptyListFUpcomingBooking.visibility = View.GONE
                binding.tvEmptyListFUpcomingBooking.visibility = View.GONE
                binding.shimmerBarmyUpcomingBooking.visibility = View.GONE
            } else {
                binding.ivEmptyListFUpcomingBooking.visibility = View.VISIBLE
                binding.tvEmptyListFUpcomingBooking.visibility = View.VISIBLE
            }
        }

        adapterMyUpcomingBooking.setOnItemClickListener(object : MyBookingUpcomingAdapter.onItemClickListener{
            override fun onItemClick(dataItemGetBooking: DataItemHistory) {
                Log.d(TAG, "onItemClick: ${dataItemGetBooking.id}")
                val bund = Bundle()
                bund.putParcelable("dataItemGetBooking",dataItemGetBooking)
                Navigation.findNavController(requireView()).navigate(R.id.action_myTripFragment_to_upcomingBookingDetailFragment,bund)
            }
        })
    }
}