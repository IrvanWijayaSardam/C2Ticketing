package com.ctwofinalproject.ticketing.view.ui.signup

import android.os.Bundle
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.Navigation
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentSignupBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!


    override fun onResume() {
        super.onResume()
//        data dummy Select City
        val city = resources.getStringArray(R.array.city)
        val arrayAdapterCity = ArrayAdapter(requireContext(), R.layout.drop_down_item, city)
        binding.aCtCitySignUp.setAdapter(arrayAdapterCity)

//        data dummy Select Province
        val province = resources.getStringArray(R.array.province)
        val arrayAdapterProvince = ArrayAdapter(requireContext(), R.layout.drop_down_item, province)
        binding.aCtProvinceSignUp.setAdapter(arrayAdapterProvince)
//        data dummy Select Country
        val country = resources.getStringArray(R.array.country)
        val arrayAdapterCountry = ArrayAdapter(requireContext(), R.layout.drop_down_item, country)
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

        binding?.tvHaveAnAccountSignUp?.setOnClickListener {
            goToLogin()
        }

    }
    private fun goToLogin(){
        Navigation.findNavController(requireView()).navigate(R.id.action_signupFragment_to_loginFragment)
    }
}