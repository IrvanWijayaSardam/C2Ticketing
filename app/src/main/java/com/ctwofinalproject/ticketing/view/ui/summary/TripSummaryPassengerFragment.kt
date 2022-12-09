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
import com.ctwofinalproject.ticketing.util.LoadingDialog
import com.ctwofinalproject.ticketing.view.adapter.PassengerListAdapter
import com.ctwofinalproject.ticketing.view.adapter.TicketByIdAdapter
import com.ctwofinalproject.ticketing.view.ui.booking.AddPassengerFragment
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.ctwofinalproject.ticketing.viewmodel.TripSummaryPassengerViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
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
    private val viewModelProto                                             : ProtoViewModel by viewModels()
    private val viewModelTripSummaryPassenger                              : TripSummaryPassengerViewModel by viewModels()
    private var token : String                                             = ""
    private var ticketId: String                                           = ""
    private var isLogin : Boolean                                          = false
    lateinit var dialog                                                    : Dialog
    private lateinit var  loadingDialog                                    : LoadingDialog
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
        adapterPassenger                                      = PassengerListAdapter(sharedPref.getInt("totalPassenger",1))
        loadingDialog                                         = LoadingDialog(requireActivity())
        adapterTicketById                                     = TicketByIdAdapter()
        gson                                                  = Gson()
        totalPassenger                                        = sharedPref.getInt("totalPassenger",1)
        initListener()
        setDialog()
        getArgs()
        setBottomNav()

        binding.rvPassengerList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvPassengerList.adapter = adapterPassenger
        adapterPassenger.submitList(passengerList)

        viewModelProto.dataBooking.observe(viewLifecycleOwner, {
            Log.d(TAG, "onViewCreated: ${it}")
            if(!it.totalPrice.equals("") && !it.passengerList.isNullOrEmpty()){
                viewModelTripSummaryPassenger.getTicketById(it.ticketIdDeparture)
                val typeTokenPassenger = object : TypeToken<List<Passanger>>() {}.type
                val typeTokenContactDetails = object : TypeToken<List<ContactDetails>>() {}.type
                var passenger = Gson().fromJson<List<Passanger>>(it.passengerList, typeTokenPassenger)
                var contactDetails = Gson().fromJson<List<ContactDetails>>(it.contactDetails, typeTokenContactDetails)

                totalPrice = it.totalPrice.toInt()
                ticketId = it.ticketIdDeparture
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
            } else {
                viewModelTripSummaryPassenger.getTicketById(ticketId)
            }
        })
        
        viewModelProto.dataUser.observe(viewLifecycleOwner,{
            if(it.isLogin){
                contactDetailsList.add(ContactDetails(it.firstname.toString(),it.lastname.toString(),it.email.toString(),it.phone.toString()))
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
                Log.d(TAG, "onViewCreated: ticketIdDataTicketByID ${ticketId}")
            }
        })

        viewModelTripSummaryPassenger.getStatusBooking().observe(viewLifecycleOwner, {
            if(it != null){
                when(it.code){
                    200 -> {
                        loadingDialog.isDismiss()
                        viewModelProto.clearDataBooking()
                        //Navigation.findNavController(binding.root).popBackStack()
                        Navigation.findNavController(requireView()).navigate(R.id.action_tripSummaryPassengerFragment_to_bookingFragment)
                        /*
                        val bottomnav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
                        bottomnav.selectedItemId = R.id.bookingFragment
                         */
                        showSnack("Booking Succesfull")
                    }
                    else -> {
                        loadingDialog.isDismiss()
                        showSnack("an error occurred, please try again")
                    }
                }
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
            Log.d(TAG, "initListener: passengerList ${passengerList.size}")
            if (isLogin){
                loadingDialog.startLoading()
                viewModelTripSummaryPassenger.submitBooking("bearer "+token,TicketData(passengerList, Ticket(ticketId.toInt(),null,totalPrice)))
                for(i in 0 until passengerList.size){
                    Log.d(TAG, "initListener: ${passengerList[i].firstname}")
                }
            } else {
                when{
                    contactDetailsList.isNullOrEmpty() ->  showSnack("Please Insert Contact Details")
                    passengerList.isNullOrEmpty() -> showSnack("Please Insert Data Passenger")
                    else -> {
                        var jsonPassengerListToJson : String = gson.toJson(passengerList)
                        var jsonContactDetailListToJson : String = gson.toJson(contactDetailsList)
                        Log.d(TAG, "setDialog: jsonPassengerListToJson ${jsonPassengerListToJson}")
                        Log.d(TAG, "setDialog: jsonPassengerListToJson ${jsonContactDetailListToJson}")

                        viewModelProto.submitDataOneWay(ticketId,jsonPassengerListToJson,jsonContactDetailListToJson,totalPrice.toString())
                        dialog.dismiss()
                        Navigation.findNavController(requireView()).navigate(R.id.action_tripSummaryPassengerFragment_to_loginFragment)
                    }
                }
            }
        }
    }

    private fun initListener() {
        binding?.run {
            ivGotoBackFromFragmentTripSummaryPassenger.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }
            btnSavePassengerFragmentATripSummary.setOnClickListener {
                when {
                    contactDetailsList.isNullOrEmpty() ->  showSnack("Please Insert Contact Details")
                    passengerList.isNullOrEmpty() -> showSnack("Please Insert Data Passenger")
                    else -> {
                        if(isLogin){
                            when{
                                passengerList.isNullOrEmpty() -> showSnack("Please Insert Data Passenger")
                                else -> {
                                    loadingDialog.startLoading()
                                    viewModelTripSummaryPassenger.submitBooking("bearer "+token,TicketData(passengerList, Ticket(ticketId.toInt(),null,totalPrice)))
                                }
                            }
                        } else {
                            dialog.show()
                        }
                    }
                }

            }
            btnAddContactDetailsSummaryPassenger.setOnClickListener {
                fragmentContactDetails.show(requireActivity().supportFragmentManager,fragmentContactDetails.tag)
            }
        }
    }

    fun showSnack(message: String){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun getArgs(){
        ticketId = arguments?.getString("ticketId","").toString()
    }

    private fun setBottomNav(){
        val navBar                                     = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility                             = View.GONE
    }
}