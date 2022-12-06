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
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentHomeBinding
import com.ctwofinalproject.ticketing.databinding.ItemRecentSearchBinding
import com.ctwofinalproject.ticketing.entity.RecentSearch
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
    lateinit var viewModelProto                                         : ProtoViewModel
    lateinit var sharedPref                                             : SharedPreferences
    lateinit var adapterRecentSearch                                    : RecentSearchAdapter
    lateinit var editPref                                               : SharedPreferences.Editor
    val homeViewModel                                                   : HomeViewModel by viewModels()
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
        viewModelProto                                      = ViewModelProvider(this).get(ProtoViewModel::class.java)
        sharedPref                                          = requireContext().getSharedPreferences("sharedairport", Context.MODE_PRIVATE)
        adapterRecentSearch                                 = RecentSearchAdapter()
        editPref                                            = sharedPref.edit()


        setImageSlider()
        setProfile()
        setBottomNav()
        initListener()


        viewModelProto.dataUser.observe(viewLifecycleOwner, {
            Log.d(TAG, "onViewCreated: ${it}")
            when{
                it == null -> binding.tvUsernameOrLogin.text = "Login"
                it.firstname != null -> binding.tvUsernameOrLogin.text = it.firstname
                else -> binding.tvUsernameOrLogin.text = "Login"
            }
        })

        homeViewModel.getAllRecentSearch().observe(viewLifecycleOwner, {
            if(it != null){
                adapterRecentSearch.submitList(it)
                binding.rvRecentSearchHomeFragment.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                binding.rvRecentSearchHomeFragment.adapter = adapterRecentSearch
            } else {
                binding.tvRecentSearch.visibility = View.GONE
                binding.tvClearAllRecent.visibility = View.GONE
                binding.rvRecentSearchHomeFragment.visibility = View.GONE
            }
        })

        adapterRecentSearch.setOnItemClickListener(object :RecentSearchAdapter.OnItemClickListener{
            override fun onItemClick(recentSearch: RecentSearch) {
                editPref.putString("airportCodeFrom",recentSearch.airportCodeFrom)
                editPref.putString("airportCodeTo",recentSearch.airportCodeTo)
                editPref.putString("departureDateForApi",recentSearch.depatureDate)
                editPref.apply()
                Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_showTicketFragment)
            }

        })


    }

    private fun setImageSlider(){
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://balicheapesttours.com/dummy/bali-tour-package9.jpg","Enjoy Trip in bali"))
        imageList.add(SlideModel("https://nicetourbali.com/wp-content/uploads/2017/04/Bali-Lombok-Tour-Packages-at-Gili-Trawangan-Island.png","Visit Lombok"))
        imageList.add(SlideModel("https://nicetourbali.com/wp-content/uploads/2018/04/Bali-Packages-India.jpg","Visit Bali"))
        imageList.add(SlideModel("https://i0.wp.com/handluggageonly.co.uk/wp-content/uploads/2017/07/HandLuggageOnly-12-4.jpg?resize=1024%2C1536&ssl=1","Visit Akrotiri Lighthouse"))

        binding.imageSlider.setImageList(imageList,ScaleTypes.FIT)
    }

    private fun setProfile(){
        Glide.with(this)
            .load("https://res.cloudinary.com/dmydy4ui3/image/upload/v1665072963/qaydexmcqchcemgwdbps.png")
            .error(R.drawable.ic_logo_ticketing)
            .circleCrop()
            .into(binding.ivProfile)
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
            tvFromAirportCodeFragmentHome.setOnClickListener {
                gotoSelectAirport("from","home")
            }
            tvToAirportCodeFragmentHome.setOnClickListener {
                gotoSelectAirport("to","home")
            }
            tvClearAllRecent.setOnClickListener {
                homeViewModel.deleteAllRecentSearch()
                tvRecentSearch.visibility = View.GONE
                tvClearAllRecent.visibility = View.GONE
                rvRecentSearchHomeFragment.visibility = View.GONE
            }

            tvFromAirportCodeFragmentHome.text = sharedPref.getString("airportCodeFrom","YIA")
            tvFromAirportNameFragmentHome.text = sharedPref.getString("airportNameFrom","AirportName")

            tvToAirportCodeFragmentHome.text   = sharedPref.getString("airportCodeTo","Select")
            tvToAirportNameFragmentHome.text   = sharedPref.getString("airportNameTo","Airport Name")
        }
    }
}


