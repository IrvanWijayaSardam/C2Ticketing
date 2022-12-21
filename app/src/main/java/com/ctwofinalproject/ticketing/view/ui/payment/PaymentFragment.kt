package com.ctwofinalproject.ticketing.view.ui.payment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentMyBookingBinding
import com.ctwofinalproject.ticketing.databinding.FragmentPaymentBinding
import com.ctwofinalproject.ticketing.model.DataItemGetBooking
import com.ctwofinalproject.ticketing.util.DecimalSeparator
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment() {
    private var _binding : FragmentPaymentBinding?                           = null
    private val binding get()                                                = _binding!!
    private var dataItemGetBooking : DataItemGetBooking?                     = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPaymentBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataItemGetBooking                                      = DataItemGetBooking()
        initListener()
        getArgs()
        setBottomNav()
    }

    private fun getArgs() {
        dataItemGetBooking = requireArguments().getParcelable("dataItemGetBooking")
        Log.d(TAG, "getArgs: ${dataItemGetBooking!!.id.toString()}")
        binding.tvFlightNumberDepFPayment.text = dataItemGetBooking!!.usersPayment!!.booking!!.ticketDeparture!!.flightId.toString()
        binding.tvDepTimeFPayment.text = dataItemGetBooking!!.usersPayment!!.booking!!.ticketDeparture!!.flight!!.departureTime.toString()
        binding.tvArrTimeFPayment.text = dataItemGetBooking!!.usersPayment!!.booking!!.ticketDeparture!!.flight!!.arrivalTime.toString()
        binding.tvPriceDepartureFPayment.setText("IDR. "+ DecimalSeparator.formatDecimalSeperators(dataItemGetBooking!!.usersPayment!!.booking!!.ticketDeparture!!.price.toString()))

        if(dataItemGetBooking!!.usersPayment!!.booking!!.ticketIdReturn != null){
            binding.tvFlightNumberReturnFPayment.text = dataItemGetBooking!!.usersPayment!!.booking!!.ticketReturn!!.flightId.toString()
            binding.tvDepTimeReturnFPayment.text = dataItemGetBooking!!.usersPayment!!.booking!!.ticketReturn!!.flight!!.departureTime.toString()
            binding.tvArrTimeReturnFPayment.text = dataItemGetBooking!!.usersPayment!!.booking!!.ticketReturn!!.flight!!.arrivalTime.toString()
            binding.tvPriceReturnFPayment.setText("IDR. "+ DecimalSeparator.formatDecimalSeperators(dataItemGetBooking!!.usersPayment!!.booking!!.ticketReturn!!.price.toString()))
        } else {
            binding.trReturnTicket.visibility = View.GONE
        }
    }

    private fun initListener() {
        binding?.run{
            ivGotoBackFromFPayment.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }
        }
    }
    private fun setBottomNav(){
        val navBar                                     = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility                             = View.GONE
    }
}