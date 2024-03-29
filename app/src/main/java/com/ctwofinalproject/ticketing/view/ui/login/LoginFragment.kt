package com.ctwofinalproject.ticketing.view.ui.login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.auth0.android.jwt.JWT
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.data.Login
import com.ctwofinalproject.ticketing.data.UserProto
import com.ctwofinalproject.ticketing.databinding.FragmentLoginBinding
import com.ctwofinalproject.ticketing.util.LoadingDialog
import com.ctwofinalproject.ticketing.util.ShowSnack
import com.ctwofinalproject.ticketing.util.TokenNav
import com.ctwofinalproject.ticketing.viewmodel.LoginViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding?                 = null
    private val binding get()                                   = _binding!!
    val viewModelProto                                          : ProtoViewModel by viewModels()
    val viewModelLogin                                          : LoginViewModel by viewModels()
    lateinit var token                                          : String
    private lateinit var  loadingDialog                         : LoadingDialog
    private lateinit var fromDestination                        : TokenNav
    private var enumFrom                                        = ""

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
        loadingDialog                                               = LoadingDialog(requireActivity())
        token                                                       = ""
        initListener()
        setBottomNav()
        getArgs()
        //clearPopBackstack()

        viewModelLogin.getToken().observe(viewLifecycleOwner) {
            if (it != null) {
                Log.d(TAG, "onViewCreated: ${it.accessToken}")
                token = it.accessToken.toString()
                val jwt = JWT(token)
                viewModelProto.editData(
                    UserProto(
                        jwt.getClaim("firstname").asString().toString(),
                        jwt.getClaim("lastname").asString().toString(),
                        jwt.getClaim("gender").asString().toString(),
                        jwt.getClaim("email").asString().toString(),
                        jwt.getClaim("phone").asString().toString(),
                        jwt.getClaim("birthdate").asString().toString(),
                        jwt.getClaim("pictures").asString().toString(),
                        token,
                        true
                    )
                )
                loadingDialog.isDismiss()
                ShowSnack.show(binding.root,"Login Berhasil")
                if(arguments?.getString("fromWhere").toString() != null){
                    Log.d(TAG, "onViewCreated: ${enumFrom}")
                    when(enumFrom){
                        "profile" -> Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_profileFragment)
                        //"tripsummary" -> Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_tripSummaryPassengerFragment)
                        else -> goToHome()
                    }
                }

            } else {
                loadingDialog.isDismiss()
                ShowSnack.show(binding.root,"Username / Password Salah")

            }
        }

    }



    private fun initListener(){
        binding?.run {
            tvDontHaveAnAccountLogin.setOnClickListener {
                goToSignUp()
            }
            btnApplyLogin.setOnClickListener {
                when {
                    tIetEmailLogin.text.isNullOrEmpty() -> tIetEmailLogin.error = "Email tidak boleh kosong"
                    tIetPasswordLogin.text.isNullOrEmpty() -> tIetPasswordLogin.error = "Password tidak boleh kosong"
                    else -> {
                        loadingDialog.startLoading()
                        viewModelLogin.auth(Login(tIetEmailLogin.text.toString(),tIetPasswordLogin.text.toString()))
                    }
                }
            }
            cvWithtoutLogin.setOnClickListener {
                viewModelProto.editData(UserProto("","","","","","","","",false))
                goToHome()
            }
        }
    }

    private fun goToSignUp(){
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_signupFragment)
    }

    private fun goToHome(){
       Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun getArgs() {
        enumFrom = arguments?.getString("fromWhere").toString()
    }

    private fun setBottomNav(){
        val navBar                                     = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility = View.GONE
    }
}