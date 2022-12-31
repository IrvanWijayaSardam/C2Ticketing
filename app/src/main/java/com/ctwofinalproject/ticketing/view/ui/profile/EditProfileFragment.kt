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
import androidx.navigation.NavInflater
import androidx.navigation.Navigation
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.util.ShowSnack
import com.ctwofinalproject.ticketing.data.UserUpdate
import com.ctwofinalproject.ticketing.databinding.FragmentDetailProfileBinding
import com.ctwofinalproject.ticketing.databinding.FragmentEditProfileBinding
import com.ctwofinalproject.ticketing.viewmodel.ProfileViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProvinceViewModel
import com.google.android.datatransport.runtime.firebase.transport.LogEventDropped
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModelProto: ProtoViewModel by viewModels()
    private val viewModelProvinces: ProvinceViewModel by viewModels()
    private val viewModelProfile: ProfileViewModel by viewModels()
    private var itemsProvince = ArrayList<String>()
    private var itemsCity = ArrayList<String>()
    private var itemNumber = ArrayList<Number>()
    private var token: String = ""
    private var password: String? = null
    private var address: String? = null
    private var countryUser: String? = null
    private var province: String? = null
    private var city: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onResume() {
        super.onResume()
        val country = resources.getStringArray(R.array.country)
        val arrayAdapterCountry = ArrayAdapter(requireContext(), R.layout.drop_down_item, country)
        binding.spCountryFEditProfile.setAdapter(arrayAdapterCountry)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val country = resources.getStringArray(R.array.country)
        initListener()

        viewModelProto.dataUser.observe(viewLifecycleOwner) {
            if (it.isLogin) {
                token = it.token
                viewModelProfile.whoami("binding "+token)
            } else {

            }
        }

        viewModelProfile.liveDataResponseWhoami.observe(viewLifecycleOwner){
            if(it != null){
                binding.edtEmailFEditProfile.setText(it.currentUser!!.email)
                binding.edtFirstNameFEditProfile.setText(it.currentUser!!.firstname)
                binding.edtLastNameFEditProfile.setText(it.currentUser!!.lastname)
                binding.edtBirthDayDateFEditProfile.setText(it.currentUser!!.birthdate!!.substring(0,10))
                binding.edtPhoneFEdiProfile.setText(it.currentUser!!.phone)
                if(it.currentUser!!.gender.equals("L")){
                    binding.rbGenderMaleFEditProfile.isChecked = true
                } else {
                    binding.rbGenderFemaleFEditProfile.isChecked = true
                }
            }
        }

        viewModelProfile.liveDataResponseUpdate.observe(viewLifecycleOwner){
            Log.d(TAG, "onViewCreated: ${it}")
            if (it != null){
                if(it.code!!.equals(200)){
                    ShowSnack.show(binding.root,"Update Profile Success")
                    viewModelProto.updateJwt(it.accessToken.toString())
                    viewModelProfile.liveDataResponseUpdate.postValue(null)
                    Navigation.findNavController(requireView()).navigate(R.id.action_editProfileFragment_to_detailProfileFragment)
                }
            }
        }

        viewModelProvinces.retrieveProvince()
        viewModelProvinces.getLiveDataProvinces().observe(viewLifecycleOwner) {
            for (i in 0 until it!!.size) {
                itemNumber.add(it.get(i).id!!.toInt())
                itemsProvince.add(it.get(i).name.toString())
            }
        }

        viewModelProvinces.getLiveDataCity().observe(viewLifecycleOwner, {
            itemsCity.clear()
            Log.d(ContentValues.TAG, "onViewCreated: Observer City : ${it}")
            for (i in 0 until it!!.size) {
                itemsCity.add(it.get(i).name.toString())
            }
        })


        binding.spCountryFEditProfile.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, id: Int, p3: Long) {
                    if (country[id].toString().equals("Indonesia")) {
                        countryUser = "Indonesia"
                        binding.spProvinceFEditProfile.visibility = View.VISIBLE
                        binding.edtProvinceFEditProfile.visibility = View.GONE
                        val arrayAdapterProvince =
                            ArrayAdapter(requireContext(), R.layout.drop_down_item, itemsProvince)
                        binding.spProvinceFEditProfile.setAdapter(arrayAdapterProvince)
                    } else {
                        countryUser = "Else"
                        binding.spCityFEditProfile.visibility = View.GONE
                        binding.edtCityFEditProfile.visibility = View.VISIBLE
                        binding.spProvinceFEditProfile.visibility = View.GONE
                        binding.edtProvinceFEditProfile.visibility = View.VISIBLE
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

        binding.spProvinceFEditProfile.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                    binding.spCityFEditProfile.visibility = View.VISIBLE
                    binding.edtCityFEditProfile.visibility = View.GONE
                    viewModelProvinces.retrieveCity(itemNumber[i].toInt())
                    val arrayAdapterCity =
                        ArrayAdapter(requireContext(), R.layout.drop_down_item, itemsCity)
                    binding.spCityFEditProfile.setAdapter(arrayAdapterCity)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    Log.d(TAG, "onNothingSelected: masuk on nothing selected")
                }
            }

        binding.spCityFEditProfile.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    //city =
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

        viewModelProto.dataUser.observe(viewLifecycleOwner) {
            binding.edtEmailFEditProfile.setText(it.email.toString())
            binding.edtFirstNameFEditProfile.setText(it.firstname.toString())
            binding.edtLastNameFEditProfile.setText(it.lastname.toString())
            when (it.gender.toString()) {
                "L" -> binding.rbGenderMaleFEditProfile.isChecked = true
                "P" -> binding.rbGenderFemaleFEditProfile.isChecked = true
            }
            binding.edtBirthDayDateFEditProfile.setText(it.birthdate.substring(0, 10))
        }
    }

    private fun initListener() {
        binding?.run {
            ivBackFEditProfile.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }
            ivBirthdayDateFEditProfile.setOnClickListener {
                val datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("CHOOSE BIRTHDAY DATE")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
                datePicker.show(
                    this@EditProfileFragment.requireActivity().supportFragmentManager,
                    "datePicker"
                )
                datePicker.addOnPositiveButtonClickListener {
                    val dateBirthFormat = SimpleDateFormat("YYYY-MM-dd")
                    val dateBirth = dateBirthFormat.format(Date(it).time)
                    edtBirthDayDateFEditProfile.setText(dateBirth)
                }
            }
            btnSubmitDataFEditProfile.setOnClickListener {
                when{
                    edtEmailFEditProfile.text.isNullOrEmpty() -> edtEmailFEditProfile.error = "You need to fill your email"
                    edtFirstNameFEditProfile.text.isNullOrEmpty() -> edtFirstNameFEditProfile.error = "You need to fill your Firstname"
                    edtLastNameFEditProfile.text.isNullOrEmpty() -> edtEmailFEditProfile.error = "You need to fill your Last name"
                    edtPhoneFEdiProfile.text.isNullOrEmpty() -> edtPhoneFEdiProfile.error = "You need to fill your Phone number"
                    edtBirthDayDateFEditProfile.text.isNullOrEmpty() -> edtBirthDayDateFEditProfile.error = "You need to fill your Birthdate"
                    edtAddressFEditProfile.text.isNullOrEmpty() -> edtHomeAddress.error = "You need to fill your home address"
                    else -> {
                        var gender = ""
                        if (rbGenderMaleFEditProfile.isChecked) {
                            gender = "L"
                        } else {
                            gender = "P"
                        }
                        if (countryUser.equals("Indonesia")) {
                            viewModelProfile.updateUser(
                                "bearer " + token, UserUpdate(
                                    edtEmailFEditProfile.text.toString(),
                                    edtFirstNameFEditProfile.text.toString(),
                                    edtLastNameFEditProfile.text.toString(),
                                    gender,
                                    edtPhoneFEdiProfile.text.toString(),
                                    edtBirthDayDateFEditProfile.text.toString(),
                                    edtHomeAddress.text.toString(),
                                    null,
                                    spCountryFEditProfile.selectedItem.toString(),
                                    spProvinceFEditProfile.selectedItem.toString(),
                                    spCityFEditProfile.selectedItem.toString()
                                )
                            )
                        } else {
                            viewModelProfile.updateUser(
                                "bearer " + token, UserUpdate(
                                    edtEmailFEditProfile.text.toString(),
                                    edtFirstNameFEditProfile.text.toString(),
                                    edtLastNameFEditProfile.text.toString(),
                                    gender,
                                    edtPhoneFEdiProfile.text.toString(),
                                    edtBirthDayDateFEditProfile.text.toString(),
                                    edtHomeAddress.text.toString(),
                                    null,
                                    spCountryFEditProfile.selectedItem.toString(),
                                    edtProvinceFEditProfile.text.toString(),
                                    edtCityFEditProfile.text.toString()
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}