package com.ctwofinalproject.ticketing.view.ui.signup

import android.content.ContentValues.TAG
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentSignupBinding
import com.ctwofinalproject.ticketing.viewmodel.ProvinceViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding?                = null
    private val binding get()                                   = _binding!!
    private lateinit var viewModelProvinces                     :  ProvinceViewModel
    private var itemsProvince = ArrayList<String>()
    private var itemsCity = ArrayList<String>()
    private var itemNumber = ArrayList<Number>()
    override fun onResume() {
        super.onResume()
//        data dummy Select City
        val city                                    = resources.getStringArray(R.array.city)
        val arrayAdapterCity                        = ArrayAdapter(requireContext(), R.layout.drop_down_item, city)
        binding.aCtCitySignUp.setAdapter(arrayAdapterCity)

//        data dummy Select Province
        val province = resources.getStringArray(R.array.province)
        val arrayAdapterProvince                    = ArrayAdapter(requireContext(), R.layout.drop_down_item, province)
        binding.aCtProvinceSignUp.setAdapter(arrayAdapterProvince)
//        data dummy Select Country
        val country                                         = resources.getStringArray(R.array.country)
        val arrayAdapterCountry                     = ArrayAdapter(requireContext(), R.layout.drop_down_item, country)
        binding.aCtCountrySignUp.setAdapter(arrayAdapterCountry)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignupBinding.inflate(inflater,container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val country                                         = resources.getStringArray(R.array.country)
        viewModelProvinces                          = ViewModelProvider(this).get(ProvinceViewModel::class.java)
        viewModelProvinces.retrieveProvince()

        binding.aCtCountrySignUp.setOnItemClickListener { adapterView, view, i, l ->
            if(country[i].toString().equals("Indonesia")) {
                val arrayAdapterProvince                    = ArrayAdapter(requireContext(), R.layout.drop_down_item, itemsProvince)
                binding.aCtProvinceSignUp.setAdapter(arrayAdapterProvince)
            }
        }

        binding.aCtProvinceSignUp.setOnItemClickListener { adapterView, view, i, l ->
            itemsCity.clear()
            viewModelProvinces.retrieveCity(itemNumber[i].toInt())
            val arrayAdapterCity                        = ArrayAdapter(requireContext(), R.layout.drop_down_item, itemsCity)
            binding.aCtCitySignUp.setAdapter(arrayAdapterCity)
        }

        viewModelProvinces.getLiveDataProvinces().observe(viewLifecycleOwner,{
            Log.d(TAG, "onViewCreated: Observer Provinces : ${it}")
            for(i in 0 until it!!.size){
                itemNumber.add(it.get(i).id!!.toInt())
                itemsProvince.add(it.get(i).name.toString())
            }
        })

        viewModelProvinces.getLiveDataCity().observe(viewLifecycleOwner,{
            Log.d(TAG, "onViewCreated: Observer City : ${it}")
            for(i in 0 until it!!.size){
                itemsCity.add(it.get(i).name.toString())
            }
        })

        binding?.tvHaveAnAccountSignUp?.setOnClickListener {
            goToLogin()
        }

    }
    private fun goToLogin(){
        Navigation.findNavController(requireView()).navigate(R.id.action_signupFragment_to_loginFragment)
    }
}