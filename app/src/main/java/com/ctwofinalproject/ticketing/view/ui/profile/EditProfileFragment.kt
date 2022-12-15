package com.ctwofinalproject.ticketing.view.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentDetailProfileBinding
import com.ctwofinalproject.ticketing.databinding.FragmentEditProfileBinding
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding?                   = null
    private val binding get()                                           = _binding!!
    private val viewModelProto                                          : ProtoViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        viewModelProto.dataUser.observe(viewLifecycleOwner){
            binding.edtEmailFEditProfile.setText(it.email.toString())
            binding.edtFirstNameFEditProfile.setText(it.firstname.toString())
            binding.edtLastNameFEditProfile.setText(it.lastname.toString())
            when(it.gender.toString()){
                "L" -> binding.rbGenderMaleFEditProfile.isChecked = true
                "P" -> binding.rbGenderFemaleFEditProfile.isChecked = true
            }
            binding.edtBirthDayDateFEditProfile.setText(it.birthdate.substring(0,10))
        }
    }

    private fun initListener() {
        binding?.run {
            ivBackFEditProfile.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }
        }
    }
}