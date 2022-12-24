package com.ctwofinalproject.ticketing.view.ui.flitpay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentConfirmPaymentBinding
import com.ctwofinalproject.ticketing.databinding.FragmentPaymentBinding
import com.ctwofinalproject.ticketing.model.DataItemGetBooking
import com.ctwofinalproject.ticketing.model.ResponsePayment
import com.ctwofinalproject.ticketing.util.DecimalSeparator
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmPaymentFragment : Fragment() {
    private var _binding : FragmentConfirmPaymentBinding?                           = null
    private val binding get()                                                       = _binding!!
    private var dataItemGetBooking: DataItemGetBooking?                             = null
    private var dataResponsePayment: ResponsePayment?                               = null
    private val viewModelProto                                                      : ProtoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentConfirmPaymentBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataItemGetBooking                                      = DataItemGetBooking()
        dataResponsePayment                                     = ResponsePayment()
        initListener()
        getArgsBooking()
        getArgsPayment()

        viewModelProto.dataUser.observe(viewLifecycleOwner){
            if(it.isLogin){
                binding.tvNameFCPayment.text = it.firstname + it.lastname
            } else {

            }
        }

    }

    private fun initListener() {
        binding?.run {
            ivBackFromFragmentConfirmPayment.setOnClickListener {
                Navigation.findNavController(requireView()).navigate(R.id.action_confirmPaymentFragment_to_myTripFragment)
            }
        }
    }

    private fun getArgsPayment() {
        dataItemGetBooking = requireArguments().getParcelable("dataItemGetBooking")
        binding.tvTotalFareFCPayment.setText("IDR "+DecimalSeparator.formatDecimalSeperators(dataItemGetBooking!!.totalPrice.toString()))
    }

    private fun getArgsBooking() {
        dataResponsePayment = requireArguments().getParcelable("dataPayment")
        binding.tvDateTransactionPayment.text = dataResponsePayment!!.data!!.paymentMutual!!.createdAt!!.substring(0,9)
        binding.idTransactionFcPayment.text = dataResponsePayment!!.data!!.paymentMutual!!.id.toString()
        binding.tvWalletId.text = dataResponsePayment!!.data!!.walletUsers!!.id.toString()

    }
}