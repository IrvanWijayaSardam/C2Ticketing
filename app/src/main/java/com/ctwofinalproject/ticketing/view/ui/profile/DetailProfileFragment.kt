package com.ctwofinalproject.ticketing.view.ui.profile

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.data.UpdatePassword
import com.ctwofinalproject.ticketing.databinding.FragmentBookingBinding
import com.ctwofinalproject.ticketing.databinding.FragmentDetailProfileBinding
import com.ctwofinalproject.ticketing.databinding.FragmentShowTicketBinding
import com.ctwofinalproject.ticketing.util.ShowSnack
import com.ctwofinalproject.ticketing.viewmodel.ProfileViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProfileFragment : Fragment() {

    private var _binding: FragmentDetailProfileBinding?                 = null
    private val binding get()                                           = _binding!!
    private val viewModelProto                                          : ProtoViewModel by viewModels()
    private val fragmentUpdatePassword                                  = UpdatePasswordFragment()
    private lateinit var builder                                        : AlertDialog.Builder
    val viewmodelProfile                                                : ProfileViewModel by viewModels()
    var token                                                           = ""
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
        builder                                               = AlertDialog.Builder(requireActivity())
        initListener()
        setBottomNav()

        viewModelProto.dataUser.observe(viewLifecycleOwner){
            if(it != null){
                binding.tvFullNameFDetailProfile.setText(it.firstname+" "+it.lastname)
                binding.tvEmailFDetailProfile.text = it.email.toString()
                binding.tvPhoneNumberFDetailProfile.text = it.phone.toString()
                token = it.token
            } else {
                Log.d(TAG, "onViewCreated: need to loggedin ")
            }
        }

        viewmodelProfile.liveDataResponseUpdatePassword.observe(viewLifecycleOwner){
            if(it != null){
                if(it.code!!.equals(200)){
                    ShowSnack.show(binding.root,"Password Updated Succesfully")
                    viewmodelProfile.liveDataResponseUpdatePassword.value = null
                } else {
                    ShowSnack.show(binding.root,"Failed to update password")
                    viewmodelProfile.liveDataResponseUpdatePassword.value = null
                }
            }
        }

        fragmentUpdatePassword.setOnItemClickListener(object : UpdatePasswordFragment.onItemClickListener{
            override fun onItemClick(
                oldPassword: String,
                newPassword: String,
                confPassword: String
            ) {
                if(newPassword.equals(confPassword)){
                    builder.setTitle("Change Password")
                        .setMessage("Are you sure want change your password ?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                            viewmodelProfile.updatePassword("bearer "+token, UpdatePassword(oldPassword, newPassword, confPassword))
                        })
                        .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                            dialogInterface.dismiss()
                        })
                        .show()
                } else {
                    ShowSnack.show(binding.root,"Password & Password confirmation not same")
                }
            }
        })
    }

    private fun initListener() {
        binding?.run {
            ivBackFDetailProfile.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }
            tvtChangeDataProfile.setOnClickListener {
                Navigation.findNavController(requireView()).navigate(R.id.action_detailProfileFragment_to_editProfileFragment)
            }
            llUpdatePasswordFDetailProfile.setOnClickListener {
                fragmentUpdatePassword.show(requireActivity().supportFragmentManager,fragmentUpdatePassword.tag)
            }
        }
    }

    private fun setBottomNav(){
        val navBar                                     = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility = View.GONE
    }

}