package com.ctwofinalproject.ticketing.view.ui.profile

import android.app.AlertDialog
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
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentHomeBinding
import com.ctwofinalproject.ticketing.databinding.FragmentProfileBinding
import com.ctwofinalproject.ticketing.util.ShowSnack
import com.ctwofinalproject.ticketing.viewmodel.HomeViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProfileViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding?                         = null
    private val binding get()                                              = _binding!!
    private val viewModelProto                                             : ProtoViewModel by viewModels()
    private val homeViewModel                                              : HomeViewModel by viewModels()
    private val profileViewModel                                           : ProfileViewModel by viewModels()
    private lateinit var builder                                           : AlertDialog.Builder
    lateinit var sharedPref                                                : SharedPreferences
    lateinit var editPref                                                  : SharedPreferences.Editor
    private var token                                                      = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        builder                                                     = AlertDialog.Builder(requireActivity())
        sharedPref                                                  = requireContext().getSharedPreferences("sharedairport", Context.MODE_PRIVATE)
        editPref                                                    = sharedPref.edit()
        initListener()
        setImageSlider()
        setProfile()
        setBottomNav()


        viewModelProto.dataUser.observe(viewLifecycleOwner){
            if(it != null){
                token = it.token
                binding.tvNameFragmentProfile.setText(it.firstname.toString()+" "+it.lastname.toString())
            } else {
                Log.d(TAG, "onViewCreated: need to be logged in")
            }
        }

    }

    private fun initListener() {
        binding?.run {
            btnLogoutFprofile.setOnClickListener {
                    builder.setTitle("Logout")
                        .setMessage("Are you sure want to logout ?")
                        .setCancelable(true)
                        .setPositiveButton("Yes",DialogInterface.OnClickListener { dialogInterface, i ->
                            clearAllLocal()
                            dialogInterface.dismiss()
                            ShowSnack.show(binding.root,"Logout Successful")
                            profileViewModel.logout("bearer "+token)
                            Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_loginFragment)
                        })
                        .setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i ->
                            dialogInterface.dismiss()
                        })
                        .show()
            }

            tvOpenMyProfileFMyProfile.setOnClickListener {
                Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_detailProfileFragment)
            }
        }
    }


    private fun setImageSlider(){
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://firebasestorage.googleapis.com/v0/b/mynotes-f6709.appspot.com/o/images%2FUpgrade%20ke%20premium.png?alt=media&token=0c04e7cd-a4e8-4ce8-9281-04a9762d1133","Premium card"))

        binding.imageSliderProfile.setImageList(imageList, ScaleTypes.FIT)
    }

    private fun setProfile(){
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/mynotes-f6709.appspot.com/o/profile%2F8?alt=media&token=5d176d41-03bf-4c56-8f39-1c7fb4e653ea")
            .error(R.drawable.ic_logo_ticketing)
            .circleCrop()
            .into(binding.ivProfileFragmentProfile)
    }
    private fun clearAllLocal(){
        viewModelProto.clearData()
        viewModelProto.clearDataBooking()
        homeViewModel.deleteAllRecentSearch()
        editPref.clear().commit()
    }

    private fun setBottomNav(){
        val navBar                                     = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility = View.VISIBLE
    }
}