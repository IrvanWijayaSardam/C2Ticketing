package com.ctwofinalproject.ticketing.view.ui.flitpay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentFlitPayBinding
import com.ctwofinalproject.ticketing.databinding.FragmentLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class FlitPayFragment : Fragment() {
    private var _binding: FragmentFlitPayBinding?                 = null
    private val binding get()                                   = _binding!!

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

}