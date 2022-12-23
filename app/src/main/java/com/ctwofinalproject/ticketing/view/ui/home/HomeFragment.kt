package com.ctwofinalproject.ticketing.view.ui.home

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.auth0.android.jwt.JWT
import com.bumptech.glide.Glide
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentHomeBinding
import com.ctwofinalproject.ticketing.databinding.ItemRecentSearchBinding
import com.ctwofinalproject.ticketing.entity.RecentSearch
import com.ctwofinalproject.ticketing.util.ShowSnack
import com.ctwofinalproject.ticketing.view.adapter.RecentSearchAdapter
import com.ctwofinalproject.ticketing.viewmodel.HomeViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding?                         = null
    private val binding get()                                           = _binding!!
    private val viewModelProto                                          : ProtoViewModel by viewModels()
    lateinit var sharedPref                                             : SharedPreferences
    lateinit var adapterRecentSearch                                    : RecentSearchAdapter
    lateinit var editPref                                               : SharedPreferences.Editor
    val homeViewModel                                                   : HomeViewModel by viewModels()
    lateinit var token                                                  : String
    var isLogin : Boolean                                               = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref                                          = requireContext().getSharedPreferences("sharedairport", Context.MODE_PRIVATE)
        editPref                                            = sharedPref.edit()
        adapterRecentSearch                                 = RecentSearchAdapter()


        setImageSlider()
        //setProfile()
        setBottomNav()
        initListener()

        viewModelProto.dataUser.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: ${it}")
            token = it.token
            if (it.isLogin) {
                isLogin = it.isLogin
                binding.tvUsernameOrLogin.text = it.firstname
                homeViewModel.getCounter("bearer "+it.token)
            } else {
                isLogin = false
                binding.tvUsernameOrLogin.text = ""
            }
        }

        homeViewModel.liveDataGetNotificationCounter.observe(viewLifecycleOwner){
            if(it != null){
                binding.tvNotificationCounter.visibility = View.VISIBLE
                binding.tvNotificationCounter.text = it.data!!.size.toString()
            } else {
                binding.tvNotificationCounter.visibility = View.GONE
            }
        }


        homeViewModel.getAllRecentSearch().observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Log.d(TAG, "onViewCreated: masuk dia ga null")
                binding.tvRecentSearch.visibility = View.VISIBLE
                binding.tvClearAllRecent.visibility = View.VISIBLE
                binding.rvRecentSearchHomeFragment.visibility = View.VISIBLE
                adapterRecentSearch.submitList(it)
                binding.rvRecentSearchHomeFragment.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.rvRecentSearchHomeFragment.adapter = adapterRecentSearch
            } else {
                binding.tvRecentSearch.visibility = View.GONE
                binding.tvClearAllRecent.visibility = View.GONE
                binding.rvRecentSearchHomeFragment.visibility = View.GONE
            }
        }

        adapterRecentSearch.setOnItemClickListener(object :RecentSearchAdapter.OnItemClickListener{
            override fun onItemClick(recentSearch: RecentSearch) {
                editPref.putString("airportCodeFrom",recentSearch.airportCodeFrom)
                editPref.putString("airportCodeTo",recentSearch.airportCodeTo)
                editPref.putString("departureDateForApi",recentSearch.depatureDate)
                editPref.putString("returnDateForApi",recentSearch.returnDate)
                editPref.apply()
                Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_showTicketFragment)
            }
        })

        viewModelProto.dataBooking.observe(viewLifecycleOwner) {
            if (!it.totalPrice.equals("")) {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_homeFragment_to_tripSummaryPassengerFragment)
            } else {
                Log.d(TAG, "onViewCreated: home fragment else")
            }
        }
    }

    private fun setImageSlider(){
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://balicheapesttours.com/dummy/bali-tour-package9.jpg","Enjoy Trip in bali"))
        imageList.add(SlideModel("https://nicetourbali.com/wp-content/uploads/2017/04/Bali-Lombok-Tour-Packages-at-Gili-Trawangan-Island.png","Visit Lombok"))
        imageList.add(SlideModel("https://nicetourbali.com/wp-content/uploads/2018/04/Bali-Packages-India.jpg","Visit Bali"))
        imageList.add(SlideModel("https://i0.wp.com/handluggageonly.co.uk/wp-content/uploads/2017/07/HandLuggageOnly-12-4.jpg?resize=1024%2C1536&ssl=1","Visit Akrotiri Lighthouse"))

        binding.imageSlider.setImageList(imageList,ScaleTypes.FIT)
    }



    private fun setBottomNav(){
        val navBar                                     = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility = View.VISIBLE
    }

    fun gotoSelectAirport(fromto : String, fFragment : String){
        var bund = Bundle()
        bund.putString("fromto",fromto)
        bund.putString("fromFragment",fFragment)
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_airportFragment,bund)
    }

    private fun initListener() {
        binding?.run {

            clNotificationFHome.setOnClickListener {
                Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_notificationFragment)
            }

            tvFromAirportCodeFragmentHome.setOnClickListener {
                gotoSelectAirport("from","home")
            }
            tvToAirportCodeFragmentHome.setOnClickListener {
                gotoSelectAirport("to","home")
            }
            tvClearAllRecent.setOnClickListener {
                tvRecentSearch.visibility = View.GONE
                tvClearAllRecent.visibility = View.GONE
                rvRecentSearchHomeFragment.visibility = View.GONE
                homeViewModel.deleteAllRecentSearch()
            }

            btnSearchFragmentHome.setOnClickListener {
                //Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_bookingFragment)
                val bottomnav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
                bottomnav.selectedItemId = R.id.bookingFragment
            }

            llWishlistFragmentHome.setOnClickListener {
                Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_wishlistFragment)
            }

            binding.llFlitPay.setOnClickListener {
                Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_flitPayFragment)
            }

            tvFromAirportCodeFragmentHome.text = sharedPref.getString("airportCodeFrom","YIA")
            tvFromAirportNameFragmentHome.text = sharedPref.getString("airportNameFrom","AirportName")

            tvToAirportCodeFragmentHome.text   = sharedPref.getString("airportCodeTo","Select")
            tvToAirportNameFragmentHome.text   = sharedPref.getString("airportNameTo","Airport Name")
        }
    }


}


