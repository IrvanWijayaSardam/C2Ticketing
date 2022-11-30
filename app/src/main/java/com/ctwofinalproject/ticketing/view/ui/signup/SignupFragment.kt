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
import com.ctwofinalproject.ticketing.data.User
import com.ctwofinalproject.ticketing.databinding.FragmentSignupBinding
import com.ctwofinalproject.ticketing.viewmodel.ProvinceViewModel
import com.ctwofinalproject.ticketing.viewmodel.RegisterViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding?                = null
    private val binding get()                                   = _binding!!
    private lateinit var viewModelProvinces                     : ProvinceViewModel
    private lateinit var viewModelRegist                        : RegisterViewModel
    private var itemsProvince                                   = ArrayList<String>()
    private var itemsCity                                       = ArrayList<String>()
    private var itemNumber                                      = ArrayList<Number>()


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
        viewModelProvinces                          = ViewModelProvider(this).get(ProvinceViewModel::class.java)
        viewModelRegist                             = ViewModelProvider(this).get(RegisterViewModel::class.java)
        viewModelProvinces.retrieveProvince()
        initListener()


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

    }

    private fun initListener(){
        binding?.run {
            tvHaveAnAccountSignUp.setOnClickListener {
                goToLogin()
            }
            ivDatePicker.setOnClickListener{
                val datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("CHOOSE BIRTHDAY DATE")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
                datePicker.show(this@SignupFragment.requireActivity().supportFragmentManager,"datePicker")
                datePicker.addOnPositiveButtonClickListener {
                    val birthdayFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val birthdayDate = birthdayFormat.format(Date(it).time)
                    tIetBirthdaySignUp.setText(birthdayDate)
                }

            }
            /*
            aCtProvinceSignUp.setOnItemClickListener { adapterView, view, i, l ->
                itemsCity.clear()
                viewModelProvinces.retrieveCity(itemNumber[i].toInt())
                val arrayAdapterCity                    = ArrayAdapter(requireContext(), R.layout.drop_down_item, itemsCity)
                binding.aCtCitySignUp.setAdapter(arrayAdapterCity)
            }
            aCtCountrySignUp.setOnItemClickListener { adapterView, view, i, l ->
                if(country[i].toString().equals("Indonesia")) {
                    val arrayAdapterProvince             = ArrayAdapter(requireContext(), R.layout.drop_down_item, itemsProvince)
                    binding.aCtProvinceSignUp.setAdapter(arrayAdapterProvince)
                }
            }
            btnApplySignUp.setOnClickListener(){
                viewModelRegist.registUser(User(tIetEmailSignUp.text.toString(), tIetFirstnameSignUp.text.toString(), tIetLastnameSignUp.text.toString(),
                "L", tIetPhoneNumberSignUp.text.toString(), tIetBirthdaySignUp.text.toString(), aCtCitySignUp.text.toString(), tIetAddressSignUp.text.toString(), tIetPasswordSignUp.text.toString(),
                tIetPasswordSignUp.text.toString(), aCtCountrySignUp.text.toString(), aCtProvinceSignUp.text.toString()))
                var user : User = User(tIetEmailSignUp.text.toString(), tIetFirstnameSignUp.text.toString(), tIetLastnameSignUp.text.toString(),
                    "L", tIetPhoneNumberSignUp.text.toString(), tIetBirthdaySignUp.text.toString(), aCtCitySignUp.text.toString(), tIetAddressSignUp.text.toString(), tIetPasswordSignUp.text.toString(),
                    tIetPasswordSignUp.text.toString(), aCtCountrySignUp.text.toString(), aCtProvinceSignUp.text.toString())
                Log.d(TAG, "initListener: ${user.toString()}")
            }
             */
        }
    }
    private fun goToLogin(){
        Navigation.findNavController(requireView()).navigate(R.id.action_signupFragment_to_loginFragment)
    }
}