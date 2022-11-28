package com.ctwofinalproject.ticketing.view.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentLoginBinding
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding?                 = null
    private val binding get()                                   = _binding!!
    lateinit var viewModelProto                                 : ProtoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelProto                                          = ViewModelProvider(this).get(ProtoViewModel::class.java)
        initListener()

    }
    private fun goToSignUp(){
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_signupFragment)
    }
    private fun initListener(){
        binding?.run {
            tvDontHaveAnAccountLogin.setOnClickListener {
                goToSignUp()
            }
            btnApplyLogin.setOnClickListener {
                viewModelProto.editData("irvan","wijaya","pria","irfanwijayasardam@gmail.com"
                ,"0813492832938","linkpictures","ada dirumah","ASJK1283123897KASJDH.ASJKDHASKDU82137",true)
            }
        }
    }
}