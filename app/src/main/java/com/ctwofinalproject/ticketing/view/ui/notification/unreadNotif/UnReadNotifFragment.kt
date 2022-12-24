package com.ctwofinalproject.ticketing.view.ui.notification.unreadNotif

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctwofinalproject.ticketing.databinding.FragmentUnReadNotifBinding
import com.ctwofinalproject.ticketing.view.adapter.UnReadNotificationAdapter
import com.ctwofinalproject.ticketing.viewmodel.NotificationViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UnReadNotifFragment : Fragment() {
    private var _binding : FragmentUnReadNotifBinding?                           = null
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
        _binding = FragmentUnReadNotifBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterUnreadNotif                                          = UnReadNotificationAdapter()

        viewModelProto.dataUser.observe(viewLifecycleOwner){
            if(it.isLogin){
                token = it.token
                viewModelNotification.getUnreadNotification("bearer "+it.token,null)
            } else {
                Log.d(ContentValues.TAG, "onViewCreated: need to be logged in")
            }
        }
        viewModelNotification.liveDataUnreadNotification.observe(viewLifecycleOwner){
            if(it != null){
                adapterUnreadNotif.submitList(it.data)
                binding.rvUnReadNotifFragmentReadNotif.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
                binding.rvUnReadNotifFragmentReadNotif.adapter = adapterUnreadNotif
            } else {
                Log.d(TAG, "onViewCreated: Notifikasi kosong")
            }
        }
    }
}