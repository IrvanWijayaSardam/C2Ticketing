package com.ctwofinalproject.ticketing.view.ui.summary

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.data.*
import com.ctwofinalproject.ticketing.databinding.FragmentTripSummaryPassengerBinding
import com.ctwofinalproject.ticketing.model.DataTicketGetById
import com.ctwofinalproject.ticketing.util.DecimalSeparator
import com.ctwofinalproject.ticketing.view.adapter.PassengerListAdapter
import com.ctwofinalproject.ticketing.view.adapter.TicketByIdAdapter
import com.ctwofinalproject.ticketing.view.ui.booking.AddPassengerFragment
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.ctwofinalproject.ticketing.viewmodel.TripSummaryPassengerViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripSummaryPassengerFragment : Fragment() {
    private var _binding : FragmentTripSummaryPassengerBinding?            = null
    private val binding get()                                              = _binding!!
    lateinit var adapterPassenger                                          : PassengerListAdapter
    private val passengerList                                              = arrayListOf<Passanger>()
    private val ticketList                                                 = arrayListOf<DataTicketGetById>()
    private val contactDetailsList                                         = arrayListOf<ContactDetails>()
    private var positionItem : Int                                         = 0
    private val fragmentAddPassenger                                       = AddPassengerFragment()
    private val fragmentContactDetails                                     = ContactDetailsFragmentDialog()
    private var isEdit: Boolean                                            = false
    lateinit var sharedPref                                                : SharedPreferences
    lateinit var sharedPrefDataBooking                                     : SharedPreferences
    lateinit var editPrefBooking                                           : SharedPreferences.Editor
    private val viewModelProto                                             : ProtoViewModel by viewModels()
    private val viewModelTripSummaryPassenger                              : TripSummaryPassengerViewModel by viewModels()
    private var token : String                                             = ""
    private var ticketId: String                                           = ""
    private var isLogin : Boolean                                          = false
    lateinit var dialog                                                    : Dialog
    lateinit var adapterTicketById                                         : TicketByIdAdapter
    private var totalPassenger: Int                                        = 0
    private var totalPrice: Int                                            = 0
    private lateinit var gson                                              : Gson

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
        sharedPrefDataBooking                                 = requireContext().getSharedPreferences("sharedBookingData",Context.MODE_PRIVATE)
        editPrefBooking                                       = sharedPrefDataBooking.edit()
        adapterPassenger                                      = PassengerListAdapter(sharedPref.getInt("totalPassenger",1))
        adapterTicketById                                     = TicketByIdAdapter()
        gson                                                  = Gson()
        totalPassenger                                        = sharedPref.getInt("totalPassenger",1)
        initListener()
        setDialog()
        getArgs()
        getDataBooking()



        binding.rvPassengerList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvPassengerList.adapter = adapterPassenger
        adapterPassenger.submitList(passengerList)

        viewModelProto.dataUser.observe(viewLifecycleOwner,{
            if(it.isLogin){
                binding.btnAddContactDetailsSummaryPassenger.visibility = View.GONE
                binding.tvNameContactDetail.setText(it.firstname+" "+it.lastname)
                binding.tvEmailContactDetail.setText(it.email)
                binding.tvPhoneNumberContactDetail.setText(it.phone)
                token = it.token.toString()
                isLogin = it.isLogin
            } else {
                binding.btnAddContactDetailsSummaryPassenger.visibility = View.VISIBLE
            }
        })
        
        viewModelTripSummaryPassenger.dataTicketById.observe(viewLifecycleOwner,{
            Log.d(TAG, "onViewCreated: ${it}")
            if(it!!.data != null){
                ticketList.add(it!!.data!!)
                adapterTicketById.submitList(ticketList)
                binding.rvTicketTripSummaryPassenger.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                binding.rvTicketTripSummaryPassenger.adapter = adapterTicketById
                binding.tvTotalFareFragmentTripSummaryPassenger.setText("IDR "+(DecimalSeparator.formatDecimalSeperators((it.data!!.price.toString().toInt() * totalPassenger).toString())))
                binding.shimmerBar.visibility = View.GONE
                binding.shimmerBarTotalFare.visibility = View.GONE
                totalPrice = it.data!!.price.toString().toInt() * totalPassenger
            } else {

            }
        })

        viewModelTripSummaryPassenger.getTicketById(ticketId)

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

        fragmentContactDetails.setOnItemClickListener(object : ContactDetailsFragmentDialog.onItemClickListener{
            override fun onItemClick(
                firstname: String,
                lastname: String,
                email: String,
                phoneNumber: String
            ) {
                contactDetailsList.add(ContactDetails(firstname, lastname, email, phoneNumber))
                binding.tvNameContactDetail.setText(firstname+" "+lastname)
                binding.tvEmailContactDetail.text = email
                binding.tvPhoneNumberContactDetail.text = phoneNumber
            }

        })

    }

    private fun getDataBooking() {
        if(!sharedPrefDataBooking.getString("ticketId","").toString().equals("")){
            val typeTokenPassenger = object : TypeToken<List<Passanger>>() {}.type
            val typeTokenContactDetails = object : TypeToken<List<ContactDetails>>() {}.type
            var passenger = Gson().fromJson<List<Passanger>>(sharedPrefDataBooking.getString("passengerList",""), typeTokenPassenger)
            var contactDetails = Gson().fromJson<List<ContactDetails>>(sharedPrefDataBooking.getString("contactDetails",""), typeTokenContactDetails)

            totalPrice = sharedPrefDataBooking.getInt("totalPrice",0)
            ticketId = sharedPrefDataBooking.getString("ticketId","").toString()
            passengerList.addAll(passenger)
            contactDetailsList.addAll(contactDetails)
            adapterPassenger.submitList(passenger)

            binding.tvNameContactDetail.setText(contactDetailsList[0].firstname+" "+contactDetailsList[0].lastname)
            binding.tvEmailContactDetail.text = contactDetails[0].email
            binding.tvPhoneNumberContactDetail.text = contactDetails[0].phoneNumber
            binding.rvTicketTripSummaryPassenger.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            binding.rvTicketTripSummaryPassenger.adapter = adapterTicketById
            binding.shimmerBar.visibility = View.GONE
            binding.shimmerBarTotalFare.visibility = View.GONE
            Log.d(TAG, "getDataBooking: ${ticketId}")
        }
    }

    private fun setDialog() {
        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.custom_dialog_need_login)
        dialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)

        val btnBack : Button = dialog.findViewById(R.id.btnCloseCustomDialogNeedLogin)
        val btnGoToLogin : Button = dialog.findViewById(R.id.btnGoToLoginCustomDialog)

        btnBack.setOnClickListener {
            dialog.dismiss()
        }

        btnGoToLogin.setOnClickListener {
            dialog.dismiss()
            Navigation.findNavController(requireView()).navigate(R.id.action_tripSummaryPassengerFragment_to_loginFragment)
        }
    }

    private fun initListener() {
        binding?.run {
            ivGotoBackFromFragmentTripSummaryPassenger.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }
            btnSavePassengerFragmentATripSummary.setOnClickListener {
                Log.d(TAG, "initListener: passengerList ${passengerList.size}")
                if (isLogin){
                    viewModelTripSummaryPassenger.submitBooking("bearer "+token,TicketData(passengerList, Ticket(ticketId.toInt(),null,totalPrice)))
                    for(i in 0 until passengerList.size){
                        Log.d(TAG, "initListener: ${passengerList[i].firstname}")
                    }
                } else {
                    var jsonPassengerListToJson : String = gson.toJson(passengerList)
                    var jsonContactDetailListToJson : String = gson.toJson(contactDetailsList)
                    editPrefBooking.putString("passengerList",jsonPassengerListToJson)
                    editPrefBooking.putString("ticketId",ticketId)
                    editPrefBooking.putString("contactDetails",jsonContactDetailListToJson)
                    editPrefBooking.putInt("totalPrice",totalPrice)
                    editPrefBooking.apply()
                    Log.d(TAG, "initListener: passengerList ${sharedPrefDataBooking.getString("passengerList","")}")
                    Log.d(TAG, "initListener: passengerList ${sharedPrefDataBooking.getString("contactDetails","")}")
                    dialog.show()
                }
            }
            btnAddContactDetailsSummaryPassenger.setOnClickListener {
                fragmentContactDetails.show(requireActivity().supportFragmentManager,fragmentContactDetails.tag)
            }
        }
    }

    private fun getArgs(){
        ticketId = arguments?.getString("ticketId","").toString()
    }
}