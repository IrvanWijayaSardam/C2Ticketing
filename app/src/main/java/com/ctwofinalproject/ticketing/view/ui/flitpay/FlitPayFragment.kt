package com.ctwofinalproject.ticketing.view.ui.flitpay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentFlitPayBinding
import com.ctwofinalproject.ticketing.databinding.FragmentLoginBinding
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlitPayFragment : Fragment() {
    private var _binding: FragmentFlitPayBinding?                 = null
    private val binding get()                                     = _binding!!
    private val viewModelProto                                    : ProtoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFlitPayBinding.inflate(inflater,container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        setBottomNav()
        setProfile()

        viewModelProto.dataUser.observe(viewLifecycleOwner){
            binding.tvProfileNameFragmentFlitPay.setText(it.firstname+" "+it.lastname)
            binding.tvProfilePhoneNumberFragmentFlitPay.text = it.phone
        }
    }

    private fun initListener() {
        binding?.run {
            ivBackFromFFlitPay.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }
        }
    }

    private fun setBottomNav(){
        val navBar                                     = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility = View.GONE
    }

    private fun setProfile(){
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/mynotes-f6709.appspot.com/o/profile%2F8?alt=media&token=5d176d41-03bf-4c56-8f39-1c7fb4e653ea")
            .error(R.drawable.ic_logo_ticketing)
            .circleCrop()
            .into(binding.ivProfileFragmentFlitPay)
    }

}