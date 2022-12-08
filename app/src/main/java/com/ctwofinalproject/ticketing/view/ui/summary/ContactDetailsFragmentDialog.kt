package com.ctwofinalproject.ticketing.view.ui.summary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentAddPassengerBinding
import com.ctwofinalproject.ticketing.databinding.FragmentContactDetailsDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ContactDetailsFragmentDialog : BottomSheetDialogFragment() {

    private var _binding: FragmentContactDetailsDialogBinding?                   = null
    private val binding get()                                                    = _binding!!
    private lateinit var listener                                                : onItemClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContactDetailsDialogBinding.inflate(inflater,container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding?.run {
            btnSaveContactDetails.setOnClickListener {
                listener.onItemClick(edtFirstNameContactDetails.text.toString(),edtLastNameContactDetails.text.toString(),edtEmailContactDetails.text.toString(),edtPhoneNumberContactDetails.text.toString())
                dismiss()
            }
        }
    }

    interface onItemClickListener{
        fun onItemClick(firstname: String,lastname: String,email: String,phoneNumber: String)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        this.listener = listener
    }

}