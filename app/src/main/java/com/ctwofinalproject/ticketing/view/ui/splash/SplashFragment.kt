package com.ctwofinalproject.ticketing.view.ui.splash


import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.auth0.android.jwt.JWT
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.data.UserProto
import com.ctwofinalproject.ticketing.databinding.FragmentSplashBinding
import com.ctwofinalproject.ticketing.util.ShowSnack
import com.ctwofinalproject.ticketing.viewmodel.HomeViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var _binding : FragmentSplashBinding?               = null
    private val binding get()                                   = _binding!!
    private val viewModelProto                                  : ProtoViewModel by viewModels()
    private val homeViewModel                                   : HomeViewModel by viewModels()
    lateinit var sharedPref                                     : SharedPreferences
    lateinit var editPref                                       : SharedPreferences.Editor

    var token : String                                          = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        sharedPref                                                  = requireContext().getSharedPreferences("sharedairport", Context.MODE_PRIVATE)
        editPref                                                    = sharedPref.edit()
        setBottomNav()

        viewModelProto.dataUser.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: ${it}")
            if (it.isLogin) {
                val jwt = JWT(it.token)
                if(!jwt.isExpired(1)){
                    android.util.Log.d(TAG, "onViewCreated: ${jwt.isExpired(1)}")
                    goToHome()
                } else {
                    android.util.Log.d(TAG, "onViewCreated: else ${jwt.isExpired(1)}")
                    ShowSnack.show(binding.root,"Session Expired , Please Re Login")
                    viewModelProto.editData(UserProto("","","","","","","","",false))
                    goToHome()
                }
            } else {
                goToLogin()
            }
        }
    }

    private fun initListener(){
        binding?.run {
            cvGetStarted?.setOnClickListener{
                goToHome()
            }
        }
    }

    private fun goToLogin(){
        Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_loginFragment)
    }
    private fun goToSignUp(){
        Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_signupFragment)
    }
    private fun goToHome(){
        Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_homeFragment)
    }
    private fun setBottomNav(){
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility = View.GONE
    }

    private fun clearAllLocal(){
        viewModelProto.clearData()
        viewModelProto.clearDataBooking()
        homeViewModel.deleteAllRecentSearch()
        editPref.clear().commit()
    }
}