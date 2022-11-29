package com.ctwofinalproject.ticketing.view.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentSplashBinding
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var _binding : FragmentSplashBinding?               = null
    private val binding get()                                   = _binding!!
    lateinit var viewModelProto                                 : ProtoViewModel

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
        viewModelProto.dataUser.observe(viewLifecycleOwner, {
            if (it.isLogin){
                goToSignUp()
            } else {
                goToLogin()
            }
        })

    }

    private fun initListener(){
        binding?.run {
            cvGetStarted?.setOnClickListener{
                goToLogin()
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
}