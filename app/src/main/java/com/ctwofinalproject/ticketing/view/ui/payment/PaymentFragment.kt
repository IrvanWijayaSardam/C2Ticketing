package com.ctwofinalproject.ticketing.view.ui.payment

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
import com.ctwofinalproject.ticketing.databinding.FragmentPaymentBinding
import com.ctwofinalproject.ticketing.model.DataItemGetBooking
import com.ctwofinalproject.ticketing.util.DecimalSeparator
import com.ctwofinalproject.ticketing.util.ShowSnack
import com.ctwofinalproject.ticketing.view.adapter.PassengerDetailsAdapter
import com.ctwofinalproject.ticketing.viewmodel.FlitPayViewModel
import com.ctwofinalproject.ticketing.viewmodel.PaymentViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment() {
    private var _binding : FragmentPaymentBinding?                           = null
    private val binding get()                                                = _binding!!
    private var dataItemGetBooking : DataItemGetBooking?                     = null
    lateinit var adapterPassengerDetails                                     : PassengerDetailsAdapter
    private val viewmodelFlitPay                                             : FlitPayViewModel by viewModels()
    private val viewmodelPayment                                             : PaymentViewModel by viewModels()
    private val viewModelProto                                               : ProtoViewModel by viewModels()
    var totalPassenger                                                       = 0
    var totalFare                                                            = 0
    var totalFareDeparture                                                   = 0
    var totalFareReturn                                                      = 0
    var token                                                                = ""
    var idBooking                                                            = 0

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
        adapterPassengerDetails                                 = PassengerDetailsAdapter(totalPassenger)
        setBottomNav()

        viewModelProto.dataUser.observe(viewLifecycleOwner){
            if(it.isLogin){
                token = it.token
                viewmodelFlitPay.getSaldo("bearer "+token)
            } else {
                Log.d(TAG, "onViewCreated: Requrie Login")
            }
        }

        viewmodelPayment.liveDataPayment.observe(viewLifecycleOwner){
            if(it != null){
                if(it.code!!.equals(200)){
                    viewmodelPayment.liveDataPayment.value = null
                    ShowSnack.show(binding.root,"Payment Succesfull , Enjoy your flight ")
                    val bund = Bundle()
                    bund.putParcelable("dataItemGetBooking",dataItemGetBooking)
                    bund.putParcelable("dataPayment",it)
                    Navigation.findNavController(requireView()).navigate(R.id.action_paymentFragment_to_confirmPaymentFragment,bund)
                } else {

                }
            }
        }

        viewmodelFlitPay.liveDataSaldo.observe(viewLifecycleOwner){
            if(it != null){
                binding.tvSaldoAmmount.setText("IDR : "+DecimalSeparator.formatDecimalSeperators(it.data!!.balance.toString()))
                if(it.data!!.balance!!.toInt() >= totalFare){
                    binding.tvSaldoStatus.text = "Saldo Mencukupi"
                    binding.btnPayFragmentPayment.isEnabled = true
                } else {
                    binding.tvSaldoStatus.text = "Saldo Tidak Cukup , Silahkan Isi Saldo Terlebih Dahulu"
                    binding.tvSaldoStatus.setTextColor(resources.getColor(R.color.red))
                    binding.btnPayFragmentPayment.isEnabled = false
                }
            } else {

            }
        }

    }

    private fun getArgs() {
        dataItemGetBooking = requireArguments().getParcelable("dataItemGetBooking")
        Log.d(TAG, "getArgs: ${dataItemGetBooking!!.id.toString()}")
        idBooking = dataItemGetBooking!!.id!!.toInt()
        binding.tvFlightNumberDepFPayment.text = dataItemGetBooking!!.usersPayment!!.booking!!.ticketDeparture!!.flightId.toString()
        binding.tvDepTimeFPayment.text = dataItemGetBooking!!.usersPayment!!.booking!!.ticketDeparture!!.flight!!.departureTime.toString()
        binding.tvArrTimeFPayment.text = dataItemGetBooking!!.usersPayment!!.booking!!.ticketDeparture!!.flight!!.arrivalTime.toString()
        binding.tvPriceDepartureFPayment.setText("IDR. "+ DecimalSeparator.formatDecimalSeperators(dataItemGetBooking!!.usersPayment!!.booking!!.ticketDeparture!!.price.toString()))
        binding.totalPassengerRowOne.text = dataItemGetBooking!!.usersPayment!!.booking!!.passangerBooking!!.size.toString()
        binding.totalPassengerRowTwo.text = dataItemGetBooking!!.usersPayment!!.booking!!.passangerBooking!!.size.toString()
        binding.tvFlightNumberDepartureBsummary.text = dataItemGetBooking!!.usersPayment!!.booking!!.ticketDeparture!!.flightId.toString()
        binding.tvPriceDepartureBsummary.setText("IDR. "+ DecimalSeparator.formatDecimalSeperators(dataItemGetBooking!!.usersPayment!!.booking!!.ticketDeparture!!.price.toString()))

        totalFareDeparture = (dataItemGetBooking!!.usersPayment!!.booking!!.ticketDeparture!!.price!!.toString().toInt() * dataItemGetBooking!!.usersPayment!!.booking!!.totalPassanger!!.toString().toInt())
        Log.d(TAG, "getArgs: totalFareDeparture : ${totalFareDeparture}")
        Log.d(TAG, "getArgs: priceDeparture : ${dataItemGetBooking!!.usersPayment!!.booking!!.ticketDeparture!!.price!!.toString()}")
        Log.d(TAG, "getArgs: totalPassenger : ${dataItemGetBooking!!.usersPayment!!.booking!!.totalPassanger!!.toString().toInt()}")

        totalFare = totalFare + totalFareDeparture
        binding.tvTotalFareDeparture.setText("IDR "+ DecimalSeparator.formatDecimalSeperators(totalFareDeparture.toString()))

        if(dataItemGetBooking!!.usersPayment!!.booking!!.ticketIdReturn != null){
            totalFareReturn = dataItemGetBooking!!.usersPayment!!.booking!!.ticketReturn!!.price!! * dataItemGetBooking!!.usersPayment!!.booking!!.totalPassanger!!
            totalFare = totalFare + totalFareReturn
            binding.tvTotalFareReturn.setText("IDR "+ DecimalSeparator.formatDecimalSeperators(totalFareReturn.toString()))
            binding.tvPriceReturnBsummary.setText("IDR. "+ DecimalSeparator.formatDecimalSeperators(dataItemGetBooking!!.usersPayment!!.booking!!.ticketReturn!!.price.toString()))
            binding.tvFlightNumberReturnFPayment.text = dataItemGetBooking!!.usersPayment!!.booking!!.ticketReturn!!.flightId.toString()
            binding.tvDepTimeReturnFPayment.text = dataItemGetBooking!!.usersPayment!!.booking!!.ticketReturn!!.flight!!.departureTime.toString()
            binding.tvArrTimeReturnFPayment.text = dataItemGetBooking!!.usersPayment!!.booking!!.ticketReturn!!.flight!!.arrivalTime.toString()
            binding.tvFlightNumberReturnBookingSummary.text = dataItemGetBooking!!.usersPayment!!.booking!!.ticketReturn!!.flightId.toString()
            binding.tvPriceReturnFPayment.setText("IDR. "+ DecimalSeparator.formatDecimalSeperators(dataItemGetBooking!!.usersPayment!!.booking!!.ticketReturn!!.price.toString()))
        } else {
            binding.trReturnTicket.visibility = View.GONE
        }
        binding.tvTotalFareFragmentPayment.setText("IDR "+ DecimalSeparator.formatDecimalSeperators(totalFare.toString()))
        adapterPassengerDetails                                 = PassengerDetailsAdapter(dataItemGetBooking!!.usersPayment!!.booking!!.passangerBooking!!.size)
        adapterPassengerDetails.submitList(dataItemGetBooking!!.usersPayment!!.booking!!.passangerBooking)
        binding.rvPassengerListFpayment.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.rvPassengerListFpayment.adapter = adapterPassengerDetails
        binding.shimmerBarTotalFare.visibility = View.GONE
    }

    private fun initListener() {
        binding?.run{
            shimmerTicketDetails.visibility = View.GONE
            ivGotoBackFromFPayment.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }
            btnPayFragmentPayment.setOnClickListener {
                viewmodelPayment.payBooking("bearer "+token,idBooking)
            }
        }
    }
    private fun setBottomNav(){
        val navBar                                     = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility                             = View.GONE
    }
}