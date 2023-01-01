package com.ctwofinalproject.ticketing.view.ui.flitpay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.data.BalanceBody
import com.ctwofinalproject.ticketing.databinding.FragmentFlitPayBinding
import com.ctwofinalproject.ticketing.databinding.FragmentTopUpSaldoBinding
import com.ctwofinalproject.ticketing.util.ShowSnack
import com.ctwofinalproject.ticketing.viewmodel.FlitPayViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProfileViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopUpSaldoFragment : Fragment() {

    private var _binding: FragmentTopUpSaldoBinding?                 = null
    private val binding get()                                        = _binding!!
    private val viewModelProto                                       : ProtoViewModel by viewModels()
    private val viewModelFlitPay                                     : FlitPayViewModel by viewModels()
    var token                                                        = ""
    var idUser                                                       = 0
    private val profileViewModel                                     : ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTopUpSaldoBinding.inflate(inflater,container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()

        viewModelProto.dataUser.observe(viewLifecycleOwner){
            if(it.isLogin){
                token = it.token
                profileViewModel.whoami("bearer "+token)
            }
        }

        profileViewModel.liveDataResponseWhoami.observe(viewLifecycleOwner){
            if(it != null){
                idUser = it.currentUser!!.userId!!.toInt()
            } else {

            }
        }

        viewModelFlitPay.liveDataResponseTopup.observe(viewLifecycleOwner){
            if(it != null){
                ShowSnack.show(binding.root,"Topup Berhasil")
                viewModelFlitPay.liveDataResponseTopup.value = null
            } else {

            }
        }



    }

    private fun initListener() {
        binding?.run {
            cvTopupSaldo.setOnClickListener {
                if(tIeTopUpSaldoFragmentTopUpSaldo.text.toString().isNullOrEmpty()){
                    tIeTopUpSaldoFragmentTopUpSaldo.error = "How much do you wanna topup ?"
                } else {
                    viewModelFlitPay.topupSaldo("bearer "+token,idUser, BalanceBody(tIeTopUpSaldoFragmentTopUpSaldo.text.toString().toInt()))
                }
            }
            ivBackFromFragmentTopUpSaldo.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }
        }
    }
}