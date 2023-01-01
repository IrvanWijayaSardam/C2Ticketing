package com.ctwofinalproject.ticketing.view.ui.mytrip

import android.content.ContentValues
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
import com.ctwofinalproject.ticketing.databinding.FragmentDetailProfileBinding
import com.ctwofinalproject.ticketing.databinding.FragmentMyUpcomingBookingBinding
import com.ctwofinalproject.ticketing.databinding.FragmentUpcomingBookingDetailBinding
import com.ctwofinalproject.ticketing.model.DataItemGetBooking
import com.ctwofinalproject.ticketing.model.DataItemHistory
import com.ctwofinalproject.ticketing.util.DecimalSeparator
import com.ctwofinalproject.ticketing.view.adapter.PassengerDetailsAdapter
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.ctwofinalproject.ticketing.viewmodel.TripSummaryPassengerViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingBookingDetailFragment : Fragment() {

    private var _binding: FragmentUpcomingBookingDetailBinding?                 = null
    private val binding get()                                                   = _binding!!
    lateinit var adapterPassengerDetails                                        : PassengerDetailsAdapter
    private var dataItemGetBooking : DataItemHistory?                           = null
    private val viewModelProto                                                  : ProtoViewModel by viewModels()
    private val viewmodelTripSummaryPassenger                                   : TripSummaryPassengerViewModel by viewModels()
    var totalPassenger                                                          = 0
    var totalFare                                                               = 0
    var totalFareDeparture                                                      = 0
    var totalFareReturn                                                         = 0
    var token                                                                   = ""
    var idBooking                                                               = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpcomingBookingDetailBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataItemGetBooking                                      = DataItemHistory()

        viewModelProto.dataUser.observe(viewLifecycleOwner){
            if(it.isLogin){
                token = it.token
            } else {
                Log.d(TAG, "onViewCreated: Require login")
            }
        }

        viewmodelTripSummaryPassenger.dataTicketById.observe(viewLifecycleOwner){
            if(it != null){
                Log.d(TAG, "onViewCreated: ${it.data}")
                binding.tvFlightNumberDepFUpcomingBookDetail.text = it.data!!.flight!!.id.toString()
                binding.tvDepTimeFUpcomingBookDetail.text = it.data!!.flight!!.departureTime
                binding.tvArrTimeFUpcomingBookDetail.text = it.data!!.flight!!.arrivalTime
                binding.tvDepatureAirportDeparture.text = it.data!!.flight!!.departureTerminal!!.name
                binding.tvArrivalTerminalDeparture.text = it.data!!.flight!!.arrivalTerminal!!.name
                binding.tvPriceDepartureFUpcomingBookDetail.text = it.data!!.price.toString()
                totalFareDeparture = totalPassenger * it.data!!.price!!.toInt()
                binding.tvTotalFareDepartureUpcomingBookDetail.setText("IDR "+DecimalSeparator.formatDecimalSeperators(totalFareDeparture.toString()))
                totalFare = totalFareReturn + totalFareDeparture
                binding.tvTotalFareFDetailBooking.setText("IDR "+DecimalSeparator.formatDecimalSeperators(totalFare.toString()))
                binding.shimmerBarTotalFare.visibility = View.GONE
            }
        }

        viewmodelTripSummaryPassenger.dataTicketReturnById.observe(viewLifecycleOwner){
            if(it != null){
                Log.d(TAG, "onViewCreated: ${it.data}")
                binding.tvFlightNumberDepFUpcomingBookDetailReturn.text = it.data!!.flight!!.id.toString()
                binding.tvDepTimeFUpcomingBookDetailReturn.text = it.data!!.flight!!.departureTime
                binding.tvArrTimeFUpcomingBookDetailReturn.text = it.data!!.flight!!.arrivalTime
                binding.tvDepatureAirportDepartureReturn.text = it.data!!.flight!!.departureTerminal!!.name
                binding.tvArrivalTerminalDepartureReturn.text = it.data!!.flight!!.arrivalTerminal!!.name
                binding.tvPriceDepartureFUpcomingBookDetailReturn.text = it.data!!.price.toString()
                totalFareReturn = totalPassenger * it.data!!.price!!.toInt()
                binding.tvTotalFareReturnUpcomingBookDetail.setText("IDR "+DecimalSeparator.formatDecimalSeperators(totalFareReturn.toString()))
                totalFare = totalFareReturn + totalFareDeparture
                binding.tvTotalFareFDetailBooking.setText("IDR "+DecimalSeparator.formatDecimalSeperators(totalFare.toString()))
                binding.shimmerBarTotalFare.visibility = View.GONE
            }
        }
        initListener()
        setBottomNav()
        getArgs()
    }

    private fun initListener() {
        binding?.run {
            ivGotoBackFUpcomingBookingDetail.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }
        }
    }

    private fun getArgs() {
        dataItemGetBooking = requireArguments().getParcelable("dataItemGetBooking")
        Log.d(TAG, "getArgs: ${dataItemGetBooking!!.userBooking!!.booking!!.ticketIdDeparture}")
        totalPassenger = dataItemGetBooking!!.userBooking!!.booking!!.totalPassanger!!.toInt()
        viewmodelTripSummaryPassenger.getTicketById(dataItemGetBooking!!.userBooking!!.booking!!.ticketIdDeparture.toString())
        binding.totalPassengerRowOneUpcomingBookDetail.text = dataItemGetBooking!!.userBooking!!.booking!!.totalPassanger.toString()

        if(dataItemGetBooking!!.userBooking!!.booking!!.ticketIdReturn != null){
            binding.tvTicketReturnDetails.visibility = View.VISIBLE
            binding.clTicketReturenDetails.visibility = View.VISIBLE
            binding.trTotalPassengerReturn.visibility = View.VISIBLE
            binding.totalPassengerRowTwoUpcomingBookDetail.text = dataItemGetBooking!!.userBooking!!.booking!!.totalPassanger.toString()
            viewmodelTripSummaryPassenger.getTicketReturnById(dataItemGetBooking!!.userBooking!!.booking!!.ticketIdReturn.toString())
        }
    }

    private fun setBottomNav(){
        val navBar                                     = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility = View.GONE
    }

}