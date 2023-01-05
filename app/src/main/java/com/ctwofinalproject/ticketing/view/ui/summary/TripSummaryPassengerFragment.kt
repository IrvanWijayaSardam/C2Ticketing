package com.ctwofinalproject.ticketing.view.ui.summary

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
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
import com.ctwofinalproject.ticketing.model.DataResponseGetTicketById
import com.ctwofinalproject.ticketing.util.DecimalSeparator
import com.ctwofinalproject.ticketing.util.LoadingDialog
import com.ctwofinalproject.ticketing.util.ShowSnack
import com.ctwofinalproject.ticketing.view.adapter.PassengerListAdapter
import com.ctwofinalproject.ticketing.view.adapter.TicketByIdAdapter
import com.ctwofinalproject.ticketing.view.adapter.TicketByIdReturnAdapter
import com.ctwofinalproject.ticketing.view.ui.booking.AddPassengerFragment
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.ctwofinalproject.ticketing.viewmodel.TripSummaryPassengerViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray

@AndroidEntryPoint
class TripSummaryPassengerFragment : Fragment() {
    private var _binding : FragmentTripSummaryPassengerBinding?            = null
    private val binding get()                                              = _binding!!
    lateinit var adapterPassenger                                          : PassengerListAdapter
    private val passengerList                                              = arrayListOf<Passanger>()
    private val ticketListDeparture                                        = arrayListOf<DataResponseGetTicketById>()
    private val ticketListReturn                                           = arrayListOf<DataResponseGetTicketById>()
    //private val contactDetailsList                                         = arrayListOf<ContactDetails>()
    private var positionItem : Int                                         = 0
    private val fragmentAddPassenger                                       = AddPassengerFragment()
    private val fragmentContactDetails                                     = ContactDetailsFragmentDialog()
    private var isEdit: Boolean                                            = false
    lateinit var sharedPref                                                : SharedPreferences
    lateinit var editPref                                                  : SharedPreferences.Editor
    private val viewModelProto                                             : ProtoViewModel by viewModels()
    private val viewModelTripSummaryPassenger                              : TripSummaryPassengerViewModel by viewModels()
    private var token : String                                             = ""
    private var ticketId: String                                           = ""
    private var ticketIdReturn: String                                     = ""
    private var isLogin : Boolean                                          = false
    lateinit var dialog                                                    : Dialog
    private lateinit var  loadingDialog                                    : LoadingDialog
    lateinit var adapterTicketById                                         : TicketByIdAdapter
    lateinit var adapterTicketByIdReturn                                   : TicketByIdReturnAdapter
    private var totalPassenger: Int                                        = 0
    private var priceTicketDeparture: Int                                  = 0
    private var priceTicketReturn: Int                                     = 0
    private var totalPrice: Int                                            = 0
    private var totalPriceFinal: Int                                       = 0
    private lateinit var gson                                              : Gson
    private lateinit var builder                                           : AlertDialog.Builder


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
        editPref                                              = sharedPref.edit()
        adapterPassenger                                      = PassengerListAdapter(sharedPref.getInt("totalPassenger",1))
        loadingDialog                                         = LoadingDialog(requireActivity())
        adapterTicketById                                     = TicketByIdAdapter()
        adapterTicketByIdReturn                               = TicketByIdReturnAdapter()
        gson                                                  = Gson()
        totalPassenger                                        = sharedPref.getInt("totalPassenger",1)
        builder                                               = AlertDialog.Builder(requireActivity())

        initListener()
        setDialog()
        setBottomNav()

        binding.rvPassengerList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvPassengerList.adapter = adapterPassenger
        adapterPassenger.submitList(passengerList)


        viewModelProto.dataBooking.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: ${it}")
            if (!it.totalPrice.equals("") && !it.passengerList.isNullOrEmpty()) {
                if(it.ticketIdReturn.isNullOrEmpty()){
                    Log.d(TAG, "onViewCreated: masukIfTicket Id Return is null or empty")
                    Log.d(TAG, "onViewCreated: tandanya dia oneway")
                    viewModelTripSummaryPassenger.getTicketById(it.ticketIdDeparture)
                    val typeTokenPassenger = object : TypeToken<List<Passanger>>() {}.type
                    var passenger =
                        Gson().fromJson<List<Passanger>>(it.passengerList, typeTokenPassenger)

                    totalPriceFinal = it.totalPrice.toInt()
                    ticketId = it.ticketIdDeparture
                    passengerList.addAll(passenger)
                    adapterPassenger.submitList(passenger)

                    binding.tvTotalFareFragmentTripSummaryPassenger.setText(
                        "IDR " + (DecimalSeparator.formatDecimalSeperators(
                            totalPriceFinal.toString()
                        ))
                    )

//                    binding.rvTicketTripSummaryPassenger.layoutManager =
//                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//                    binding.rvTicketTripSummaryPassenger.adapter = adapterTicketById
                    binding.cvTicketReturnTripSummaryPassenger.visibility = View.GONE
                    binding.cvTicketReturn.visibility = View.GONE
                    binding.shimmerBar.visibility = View.GONE
                    binding.shimmerBarTotalFare.visibility = View.GONE
                    binding.cvContactDetail.visibility = View.VISIBLE
                    Log.d(TAG, "getDataBooking: ${ticketId}")
                } else {
                    Log.d(TAG, "onViewCreated: masuk else dari ticketIdReturn observer databooking")
                    Log.d(TAG, "onViewCreated: tandanya dia Roundtrip")
                    Log.d(TAG, "onViewCreated: passengerListIt ${it.passengerList}")
                    Log.d(TAG, "onViewCreated: passengerList before submit${passengerList}")

                    viewModelTripSummaryPassenger.getTicketById(it.ticketIdDeparture)
                    viewModelTripSummaryPassenger.getTicketReturnById(it.ticketIdReturn)

                    val typeTokenPassenger = object : TypeToken<List<Passanger>>() {}.type
                    var passenger = Gson().fromJson<List<Passanger>>(it.passengerList, typeTokenPassenger)
                    Log.d(TAG, "onViewCreated: passengerList passenger${passenger}")

                    totalPriceFinal = it.totalPrice.toInt()
                    ticketId = it.ticketIdDeparture
                    ticketIdReturn = it.ticketIdReturn
                    passengerList.addAll(passenger)
                    adapterPassenger.submitList(passenger)
                    Log.d(TAG, "onViewCreated: passenger to mutableist${passenger.toMutableList()}")
                    Log.d(TAG, "onViewCreated: passengerList aftersubmit${passengerList}")

                    binding.tvTotalFareFragmentTripSummaryPassenger.setText(
                        "IDR " + (DecimalSeparator.formatDecimalSeperators(
                            totalPriceFinal.toString()
                        ))
                    )

                    binding.rvTicketReturnTripSummaryPassenger.layoutManager =  LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    binding.rvTicketReturnTripSummaryPassenger.adapter = adapterTicketByIdReturn

                    binding.rvTicketTripSummaryPassenger.layoutManager =  LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    binding.rvTicketTripSummaryPassenger.adapter = adapterTicketById


                    binding.cvTicketReturnTripSummaryPassenger.visibility = View.VISIBLE
                    binding.cvTicketReturn.visibility = View.VISIBLE
                    binding.rvTicketTripSummaryPassenger.visibility = View.VISIBLE
                    binding.rvTicketReturnTripSummaryPassenger.visibility
                    binding.shimmerBar.visibility = View.GONE
                    binding.shimmerBarTotalFare.visibility = View.GONE
                    binding.cvContactDetail.visibility = View.GONE
                    Log.d(TAG, "getDataBooking: ${ticketId}")
                    Log.d(TAG, "getDataBooking: ${ticketIdReturn}")
                }
            } else {
                Log.d(TAG, "onViewCreated: lewatsini")
                binding.cvContactDetail.visibility = View.GONE
                ticketId = it.ticketIdDeparture
                ticketIdReturn = it.ticketIdReturn
                viewModelTripSummaryPassenger.getTicketById(ticketId)
                viewModelTripSummaryPassenger.getTicketReturnById(ticketIdReturn)
            }
        }

        viewModelProto.dataUser.observe(viewLifecycleOwner) {
            if (it != null){
                token = it.token.toString()
                isLogin = it.isLogin
                if (it.isLogin) {
                    Log.d(TAG, "onViewCreated: cuy lewat sini")
                    binding.cvContactDetail.visibility = View.VISIBLE
                    binding.btnCancelBookingFragmentATripSummary.visibility = View.VISIBLE
                    binding.tvNameContactDetail.setText(it.firstname + " " + it.lastname)
                    binding.tvEmailContactDetail.setText(it.email)
                    binding.tvPhoneNumberContactDetail.setText(it.phone)
                } else {
                    isLogin = false
                }
            }
        }

        viewModelTripSummaryPassenger.dataResponseWishlist.observe(viewLifecycleOwner){
            Log.d(TAG, "onViewCreated: ${it}")
            if(it!= null){
                if(it.code!!.equals(200)){
                    ShowSnack.show(binding.root,"Wishlist Created")
                }
            }
        }

        viewModelTripSummaryPassenger.dataTicketById.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: ${it}")
            if (it!!.data != null) {
                if(isLogin){
                    //Log.d(TAG, "onViewCreated: kok lewat sini")
                    ticketListDeparture.add(it!!.data!!)
                    adapterTicketById.submitList(ticketListDeparture)
                    binding.rvTicketTripSummaryPassenger.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    binding.rvTicketTripSummaryPassenger.adapter = adapterTicketById

                    priceTicketDeparture = it.data!!.price.toString().toInt() * totalPassenger
                    totalPrice = totalPrice + priceTicketDeparture

                    binding.tvTotalFareFragmentTripSummaryPassenger.setText(
                        "IDR " + (DecimalSeparator.formatDecimalSeperators(
                            totalPrice.toString()
                        ))
                    )

                    binding.shimmerBar.visibility = View.GONE
                    binding.shimmerBarTotalFare.visibility = View.GONE
                    binding.cvContactDetail.visibility = View.VISIBLE
                } else {
                    Log.d(TAG, "onViewCreated: kok lewat sini 2")
                    ticketListDeparture.add(it!!.data!!)
                    adapterTicketById.submitList(ticketListDeparture)
                    binding.rvTicketTripSummaryPassenger.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    binding.rvTicketTripSummaryPassenger.adapter = adapterTicketById

                    priceTicketDeparture = it.data!!.price.toString().toInt() * totalPassenger
                    totalPrice = totalPrice + priceTicketDeparture

                    binding.tvTotalFareFragmentTripSummaryPassenger.setText(
                        "IDR " + (DecimalSeparator.formatDecimalSeperators(
                            totalPrice.toString()
                        ))
                    )

                    binding.shimmerBar.visibility = View.GONE
                    binding.shimmerBarTotalFare.visibility = View.GONE
                    binding.cvContactDetail.visibility = View.GONE
                }
                //totalPrice = it.data!!.price.toString().toInt() * totalPassenger
            } else {
                binding.cvContactDetail.visibility = View.GONE
                Log.d(TAG, "onViewCreated: ticketIdDataTicketByID ${ticketId}")
            }
        }

        viewModelTripSummaryPassenger.dataTicketReturnById.observe(viewLifecycleOwner){
            Log.d(TAG, "onViewCreated: dataTicket returnById ${it}")
            if (it != null) {
                //Log.d(TAG, "onViewCreated: bang lewat sini bang")
                ticketListReturn.add(it!!.data!!)
                adapterTicketByIdReturn.submitList(ticketListReturn)
                binding.rvTicketReturnTripSummaryPassenger.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvTicketReturnTripSummaryPassenger.adapter = adapterTicketByIdReturn

                priceTicketReturn = it.data!!.price.toString().toInt() * totalPassenger
                totalPrice = totalPrice + priceTicketReturn

                binding.tvTotalFareFragmentTripSummaryPassenger.setText(
                    "IDR " + (DecimalSeparator.formatDecimalSeperators(
                        totalPrice.toString()
                    ))
                )
                //binding.cvContactDetail.visibility = View.VISIBLE
                binding.shimmerBarReturn.visibility = View.GONE
                binding.shimmerBarTotalFare.visibility = View.GONE
                //totalPrice = it.data!!.price.toString().toInt() * totalPassenger
            } else {
                binding.cvTicketReturnTripSummaryPassenger.visibility = View.GONE
                binding.cvTicketReturn.visibility = View.GONE
                binding.shimmerBarReturn.visibility = View.GONE
                Log.d(TAG, "onViewCreated: ticketIdDataTicketByID ${ticketIdReturn}")
            }
        }

        viewModelTripSummaryPassenger.getStatusBooking().observe(viewLifecycleOwner) {
            if (it != null) {
                when (it.code) {
                    200 -> {
                        loadingDialog.isDismiss()
                        editPref.clear().commit()
                        viewModelProto.clearDataBooking()
                        Navigation.findNavController(requireView())
                            .navigate(R.id.action_tripSummaryPassengerFragment_to_bookingFragment)
                        showSnack("Booking Succesfull")
                    }
                    else -> {
                        loadingDialog.isDismiss()
                        showSnack("an error occurred, please try again")
                    }
                }
            }
        }


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
                //contactDetailsList.add(ContactDetails(firstname, lastname, email, phoneNumber))
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
                Log.d(TAG, "setDialog: ${totalPrice}")
                Log.d(TAG, "setDialog: ${totalPriceFinal}")

                viewModelTripSummaryPassenger.submitBooking("bearer "+token,TicketData(passengerList, Ticket(ticketId.toInt(),null,totalPrice)))
                for(i in 0 until passengerList.size){
                    Log.d(TAG, "initListener: ${passengerList[i].firstname}")
                }
            } else {
                when{
                    passengerList.isNullOrEmpty() -> showSnack("Please Insert Data Passenger")
                    else -> {
                        var jsonPassengerListToJson : String = gson.toJson(passengerList)
                        //var jsonContactDetailListToJson : String = gson.toJson(contactDetailsList)
                        Log.d(TAG, "setDialog: jsonPassengerListToJson ${jsonPassengerListToJson}")
                        //Log.d(TAG, "setDialog: jsonPassengerListToJson ${jsonContactDetailListToJson}")
                        if(ticketIdReturn.isNullOrEmpty()){
                            viewModelProto.submitDataOneWay(ticketId,jsonPassengerListToJson,totalPrice.toString())
                        } else {
                            viewModelProto.submitDataTwoWay(ticketId,ticketIdReturn,jsonPassengerListToJson,totalPrice.toString())
                        }
                        dialog.dismiss()
                        var bund = Bundle()
                        bund.putString("fromWhere", "tripsummary")
                        Navigation.findNavController(requireView()).navigate(R.id.action_tripSummaryPassengerFragment_to_loginFragment,bund)
                    }
                }
            }
        }
    }

    private fun initListener() {
        binding?.run {

            btnCancelBookingFragmentATripSummary.setOnClickListener {
                builder.setTitle("Cancel Booking")
                    .setMessage("Are you sure want to cancel this booking process ?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                        editPref.clear().commit()
                        viewModelProto.clearDataBooking()
                        dialogInterface.dismiss()
                        ShowSnack.show(binding.root,"Booking process cancelled")
                        Navigation.findNavController(requireView()).navigate(R.id.action_tripSummaryPassengerFragment_to_bookingFragment)
                    })
                    .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.dismiss()
                    })
                    .setNeutralButton("Add To Wishlist", DialogInterface.OnClickListener { dialogInterface, i ->
                        if(ticketIdReturn.isNullOrEmpty()){
                            viewModelTripSummaryPassenger.postWishlist("bearer "+token,CreateWishlist(ticketId.toInt(),null))
                        } else {
                            viewModelTripSummaryPassenger.postWishlist("bearer "+token,CreateWishlist(ticketId.toInt(),ticketIdReturn.toInt()))
                        }
                        editPref.clear().commit()
                        viewModelProto.clearDataBooking()
                        dialogInterface.dismiss()
                        Navigation.findNavController(requireView()).navigate(R.id.action_tripSummaryPassengerFragment_to_wishlistFragment)
                    })
                    .show()
            }

            ivGotoBackFromFragmentTripSummaryPassenger.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }
            btnSavePassengerFragmentATripSummary.setOnClickListener {
                when {
                    passengerList.isNullOrEmpty() -> showSnack("Please Insert Data Passenger")
                    else -> {
                        if(isLogin){
                            when{
                                passengerList.isNullOrEmpty() -> showSnack("Please Insert Data Passenger")
                                else -> {
                                    loadingDialog.startLoading()
                                    if(ticketIdReturn.isNullOrEmpty()){
                                        Log.d(TAG, "initListener: oneWay")
                                        //totalPrice = totalPriceFinal
                                        if(totalPriceFinal.equals(0)){
                                            viewModelTripSummaryPassenger.submitBooking("bearer "+token,TicketData(passengerList, Ticket(ticketId.toInt(),null,totalPrice)))
                                        } else {
                                            viewModelTripSummaryPassenger.submitBooking("bearer "+token,TicketData(passengerList, Ticket(ticketId.toInt(),null,totalPriceFinal)))
                                        }
                                    } else {
                                        //totalPrice = totalPriceFinal
                                        Log.d(TAG, "initListener: roundTrip")
                                        if(totalPriceFinal.equals(0)){
                                            viewModelTripSummaryPassenger.submitBooking("bearer "+token,TicketData(passengerList, Ticket(ticketId.toInt(),ticketIdReturn.toInt(),totalPrice)))
                                        } else {
                                            viewModelTripSummaryPassenger.submitBooking("bearer "+token,TicketData(passengerList, Ticket(ticketId.toInt(),ticketIdReturn.toInt(),totalPriceFinal)))
                                        }
                                    }
                                }
                            }
                        } else {
                            dialog.show()
                        }
                    }
                }
            }
        }
    }

    fun showSnack(message: String){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setBottomNav(){
        val navBar                                     = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility                             = View.GONE
    }
}