package com.ctwofinalproject.ticketing.view.ui.summary

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctwofinalproject.ticketing.data.Passanger
import com.ctwofinalproject.ticketing.data.Ticket
import com.ctwofinalproject.ticketing.data.TicketData
import com.ctwofinalproject.ticketing.databinding.FragmentTripSummaryPassengerBinding
import com.ctwofinalproject.ticketing.view.adapter.PassengerListAdapter
import com.ctwofinalproject.ticketing.view.ui.booking.AddPassengerFragment
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.ctwofinalproject.ticketing.viewmodel.TripSummaryPassengerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripSummaryPassengerFragment : Fragment() {
    private var _binding : FragmentTripSummaryPassengerBinding?            = null
    private val binding get()                                              = _binding!!
    lateinit var adapterPassenger                                          : PassengerListAdapter
    private val passengerList                                              = arrayListOf<Passanger>()
    private var positionItem : Int                                         = 0
    private val fragmentAddPassenger                                       = AddPassengerFragment()
    private var isEdit: Boolean                                            = false
    lateinit var sharedPref                                                : SharedPreferences
    private val viewModelProto                                             : ProtoViewModel by viewModels()
    private val viewModelTripSummaryPassenger                              : TripSummaryPassengerViewModel by viewModels()
    private var token : String                                             = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripSummaryPassengerBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref                                            = requireContext().getSharedPreferences("sharedairport", Context.MODE_PRIVATE)
        adapterPassenger                                      = PassengerListAdapter(sharedPref.getInt("totalPassenger",1))
        initListener()

        binding.rvPassengerList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvPassengerList.adapter = adapterPassenger
        adapterPassenger.submitList(passengerList)

        viewModelProto.dataUser.observe(viewLifecycleOwner,{
            if(it != null){
                binding.tvNameContactDetail.setText(it.firstname+" "+it.lastname)
                binding.tvEmailContactDetail.setText(it.email)
                binding.tvPhoneNumberContactDetail.setText(it.phone)
                token = it.token.toString()
            } else {
                //Suruh insert data contact
            }
        })

        adapterPassenger.setOnItemClickListener(object : PassengerListAdapter.OnItemClickListener{
            override fun onItemClick(passenger: Passanger?, position: Int) {
                isEdit = false
                positionItem = position
                if(passenger != null){
                    isEdit = true
                    fragmentAddPassenger.getPassenger(passenger)
                }
                fragmentAddPassenger.show(requireActivity().supportFragmentManager,fragmentAddPassenger.tag)
            }
        })




        fragmentAddPassenger.setOnItemClickListener(object : AddPassengerFragment.onItemClickListener{
            override fun onItemClick(passenger: Passanger) {
                if(!isEdit){
                    passengerList.add(passenger)
                } else {
                    passengerList[positionItem] = passenger
                }
                adapterPassenger.modifyItemList(passengerList,positionItem)
                Log.d(TAG, "onItemClick: ${passenger}")
            }
        })
    }

    private fun initListener() {
        binding?.run {
            ivGotoBackFromFragmentTripSummaryPassenger.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }
            btnSavePassengerFragmentATripSummary.setOnClickListener {
                Log.d(TAG, "initListener: passengerList ${passengerList.size}")
                viewModelTripSummaryPassenger.submitBooking("bearer "+token,TicketData(passengerList, Ticket(690000,1,1)))
                for(i in 0 until passengerList.size){
                    Log.d(TAG, "initListener: ${passengerList[i].firstname}")
                }
            }

        }
    }
}