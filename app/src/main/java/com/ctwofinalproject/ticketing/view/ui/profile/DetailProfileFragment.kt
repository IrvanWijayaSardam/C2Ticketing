package com.ctwofinalproject.ticketing.view.ui.profile

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentBookingBinding
import com.ctwofinalproject.ticketing.databinding.FragmentDetailProfileBinding
import com.ctwofinalproject.ticketing.databinding.FragmentShowTicketBinding
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProfileFragment : Fragment() {

    private var _binding: FragmentDetailProfileBinding?                 = null
    private val binding get()                                           = _binding!!
    private val viewModelProto                                          : ProtoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailProfileBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        setBottomNav()

        viewModelProto.dataUser.observe(viewLifecycleOwner){
            if(it != null){
                binding.tvFullNameFDetailProfile.setText(it.firstname+" "+it.lastname)
                binding.tvEmailFDetailProfile.text = it.email.toString()
                binding.tvPhoneNumberFDetailProfile.text = it.phone.toString()
            } else {
                Log.d(TAG, "onViewCreated: need to loggedin ")
            }
        }
    }

    private fun initListener() {
        binding?.run {
            ivBackFDetailProfile.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }
            tvtChangeDataProfile.setOnClickListener {
                Navigation.findNavController(requireView()).navigate(R.id.action_detailProfileFragment_to_editProfileFragment)
            }
        }
    }

    private fun setBottomNav(){
        val navBar                                     = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility = View.GONE
    }

}