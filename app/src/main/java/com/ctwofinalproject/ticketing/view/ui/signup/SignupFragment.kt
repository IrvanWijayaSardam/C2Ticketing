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
import com.google.android.material.snackbar.Snackbar
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

        viewModelRegist.getStatusRegist().observe(viewLifecycleOwner, {
            if(it != null){
                showSnack("Registrasi Berhasil")
                goToLogin()
            } else {
                showSnack("Registrasi Gagal")
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
             */
            btnApplySignUp.setOnClickListener(){
                when{
                    tIetEmailSignUp.text.isNullOrEmpty() -> tIetEmailSignUp.error = "email tidak boleh kosong"
                    tIetFirstnameSignUp.text.isNullOrEmpty() -> tIetFirstnameSignUp.error = "firstname tidak boleh kosong"
                    tIetLastnameSignUp.text.isNullOrEmpty() -> tIetLastnameSignUp.error = "lastname tidak boleh kosong"
                    tIetPhoneNumberSignUp.text.isNullOrEmpty() -> tIetPhoneNumberSignUp.error = "nomor telpon tidak boleh kosong"
                    tIetBirthdaySignUp.text.isNullOrEmpty() -> tIetBirthdaySignUp.error = "tanggal lahir tidak boleh kosong"
                    tIetPasswordSignUp.text.isNullOrEmpty() -> tIetPasswordSignUp.error = "password tidak boleh kosong"
                    tIetConfPasswordSignUp.text.isNullOrEmpty() -> tIetConfPasswordSignUp.error = "konfirmasi password tidak boleh kosong"
                    else -> {
                        if(rbGenderMaleFragmentSignUp.isChecked) {
                            if(tIetPasswordSignUp.text.toString().equals(tIetConfPasswordSignUp.text.toString())){
                                viewModelRegist.registUser(User(tIetEmailSignUp.text.toString(), tIetFirstnameSignUp.text.toString(), tIetLastnameSignUp.text.toString(), "L","62"+tIetPhoneNumberSignUp.text.toString(), tIetBirthdaySignUp.text.toString(), tIetPasswordSignUp.text.toString(), tIetConfPasswordSignUp.text.toString()))
                            } else {
                                tIetConfPasswordSignUp.error = "konfirmasi password tidak sama"
                            }
                        } else if (rbGenderFemaleFragmentSignUp.isChecked){
                            if(tIetPasswordSignUp.text.toString().equals(tIetConfPasswordSignUp.text.toString())){
                                viewModelRegist.registUser(User(tIetEmailSignUp.text.toString(), tIetFirstnameSignUp.text.toString(), tIetLastnameSignUp.text.toString(), "P", "62"+tIetPhoneNumberSignUp.text.toString(), tIetBirthdaySignUp.text.toString(), tIetPasswordSignUp.text.toString(), tIetConfPasswordSignUp.text.toString()))
                            } else {
                                tIetConfPasswordSignUp.error = "konfirmasi password tidak sama"
                            }
                        } else {
                            rbGenderMaleFragmentSignUp.error = "gender belum dipilih"
                            rbGenderFemaleFragmentSignUp.error = "gender belum dipilih"
                        }
                    }
                }
            }
        }
    }
    private fun goToLogin(){
        Navigation.findNavController(requireView()).navigate(R.id.action_signupFragment_to_loginFragment)
    }
    fun showSnack(message: String){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

}