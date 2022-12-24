package com.ctwofinalproject.ticketing.view.ui.notification.readNotif

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentReadNotifBinding
import com.ctwofinalproject.ticketing.databinding.FragmentUnReadNotifBinding
import com.ctwofinalproject.ticketing.view.adapter.UnReadNotificationAdapter
import com.ctwofinalproject.ticketing.viewmodel.NotificationViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReadNotifFragment : Fragment() {
    private var _binding : FragmentReadNotifBinding?                             = null
    private val binding get()                                                    = _binding!!
    private val viewModelNotification                                            : NotificationViewModel by viewModels()
    private val viewModelProto                                                   : ProtoViewModel by viewModels()
    var token                                                                    = ""
    lateinit var adapterUnreadNotif                                              : UnReadNotificationAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReadNotifBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterUnreadNotif                                          = UnReadNotificationAdapter()

        viewModelProto.dataUser.observe(viewLifecycleOwner){
            if(it.isLogin){
                token = it.token
                viewModelNotification.getUnreadNotification("bearer "+it.token,"1")
            } else {
                Log.d(ContentValues.TAG, "onViewCreated: need to be logged in")
            }
        }
        viewModelNotification.liveDataUnreadNotification.observe(viewLifecycleOwner){
            if(it != null){
                adapterUnreadNotif.submitList(it.data)
                binding.rvReadNotifFragmentReadNotif.layoutManager = LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL, false)
                binding.rvReadNotifFragmentReadNotif.adapter = adapterUnreadNotif
            } else {
                Log.d(ContentValues.TAG, "onViewCreated: Notifikasi kosong")
            }
        }

    }
}