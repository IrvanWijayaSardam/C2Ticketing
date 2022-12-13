package com.ctwofinalproject.ticketing.view.ui.splash


import android.content.ContentValues.TAG
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.auth0.android.jwt.JWT
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentSplashBinding
import com.ctwofinalproject.ticketing.util.ShowSnack
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var _binding : FragmentSplashBinding?               = null
    private val binding get()                                   = _binding!!
    lateinit var viewModelProto                                 : ProtoViewModel
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
        viewModelProto                                          = ViewModelProvider(this).get(ProtoViewModel::class.java)
        setBottomNav()

        viewModelProto.dataUser.observe(viewLifecycleOwner) {
            if (it.isLogin) {
                val jwt = JWT(it.token)
                if(!jwt.isExpired(1)){
                    android.util.Log.d(TAG, "onViewCreated: ${jwt.isExpired(1)}")
                    goToHome()
                } else {
                    android.util.Log.d(TAG, "onViewCreated: else ${jwt.isExpired(1)}")
                    ShowSnack.show(binding.root,"Session Expired , Please Re Login")
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
}