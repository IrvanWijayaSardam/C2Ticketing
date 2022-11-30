package com.ctwofinalproject.ticketing.view.ui.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentHomeBinding
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding?             = null
    private val binding get()                               = _binding!!
    lateinit var viewModelProto                             : ProtoViewModel

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
        setImageSlider()
        setProfile()
        setBottomNav()


        viewModelProto.dataUser.observe(viewLifecycleOwner, {
            Log.d(TAG, "onViewCreated: ${it}")
            when{
                it == null -> binding.tvUsernameOrLogin.text = "Login"
                it.firstname != null -> binding.tvUsernameOrLogin.text = it.firstname
                else -> binding.tvUsernameOrLogin.text = "Login"
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
}