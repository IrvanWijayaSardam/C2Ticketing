package com.ctwofinalproject.ticketing.view.ui.profile

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentDetailProfileBinding
import com.ctwofinalproject.ticketing.databinding.FragmentEditProfileBinding
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProvinceViewModel
import com.google.android.datatransport.runtime.firebase.transport.LogEventDropped
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding?                   = null
    private val binding get()                                           = _binding!!
    private val viewModelProto                                          : ProtoViewModel by viewModels()
    private val viewModelProvinces                                      : ProvinceViewModel by viewModels()
    private var itemsProvince = ArrayList<String>()
    private var itemsCity = ArrayList<String>()
    private var itemNumber = ArrayList<Number>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater,container,false)
        return binding?.root
    }


    override fun onResume() {
        super.onResume()
//        data dummy Select City
//        val city                                    = resources.getStringArray(R.array.city)
//        val arrayAdapterCity                        = ArrayAdapter(requireContext(), R.layout.drop_down_item, city)
//        binding.spCityFEditProfile.setAdapter(arrayAdapterCity)
//
////        data dummy Select Province
//        val province = resources.getStringArray(R.array.province)
//        val arrayAdapterProvince                    = ArrayAdapter(requireContext(), R.layout.drop_down_item, province)
//        binding.spProvinceFEditProfile.setAdapter(arrayAdapterProvince)
////        data dummy Select Country
        val country                                         = resources.getStringArray(R.array.country)
        val arrayAdapterCountry                     = ArrayAdapter(requireContext(), R.layout.drop_down_item, country)
        binding.spCountryFEditProfile.setAdapter(arrayAdapterCountry)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val country                                         = resources.getStringArray(R.array.country)
        initListener()

        viewModelProvinces.retrieveProvince()
        viewModelProvinces.getLiveDataProvinces().observe(viewLifecycleOwner){
            for(i in 0 until it!!.size){
                itemNumber.add(it.get(i).id!!.toInt())
                itemsProvince.add(it.get(i).name.toString())
            }
        }

        viewModelProvinces.getLiveDataCity().observe(viewLifecycleOwner,{
            Log.d(ContentValues.TAG, "onViewCreated: Observer City : ${it}")
            for(i in 0 until it!!.size){
                itemsCity.add(it.get(i).name.toString())
            }
        })


        binding.spCountryFEditProfile.onItemSelectedListener = object :AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, id: Int, p3: Long) {
                if(country[id].toString().equals("Indonesia")){
                    val arrayAdapterProvince = ArrayAdapter(requireContext(),R.layout.drop_down_item,itemsProvince)
                    binding.spProvinceFEditProfile.setAdapter(arrayAdapterProvince)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.spProvinceFEditProfile.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                itemsCity.clear()
                viewModelProvinces.retrieveCity(itemNumber[i].toInt())
                val arrayAdapterCity                        = ArrayAdapter(requireContext(), R.layout.drop_down_item, itemsCity)
                binding.spCityFEditProfile.setAdapter(arrayAdapterCity)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d(TAG, "onNothingSelected: masuk on nothing selected")
            }

        }

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