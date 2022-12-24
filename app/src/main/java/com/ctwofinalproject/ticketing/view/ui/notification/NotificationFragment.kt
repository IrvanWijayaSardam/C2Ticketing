package com.ctwofinalproject.ticketing.view.ui.notification

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentHomeBinding
import com.ctwofinalproject.ticketing.databinding.FragmentNotificationBinding
import com.ctwofinalproject.ticketing.view.ui.notification.readNotif.ReadNotifFragment
import com.ctwofinalproject.ticketing.view.ui.notification.unreadNotif.UnReadNotifFragment
import com.ctwofinalproject.ticketing.viewmodel.NotificationViewModel
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {
    private var _binding : FragmentNotificationBinding?                         = null
    private val binding get()                                                   = _binding!!
    private val viewModelNotification                                           : NotificationViewModel by viewModels()
    private val viewModelProto                                                  : ProtoViewModel by viewModels()
    var token                                                                   = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotificationBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomNav()
        initListener()

        viewModelProto.dataUser.observe(viewLifecycleOwner){
            if(it.isLogin){
                token = it.token
                viewModelNotification.getUnreadNotification("bearer "+it.token,null)
                viewModelNotification.readAllNotification("bearer "+it.token)
            } else {
                Log.d(TAG, "onViewCreated: need to be logged in")
            }
        }


    }

    private fun initListener() {
        binding?.run {
            ivGotoBackFromFragmentNotification.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }

            cvUnReadNotifFragmentNotification.setOnClickListener {
                cvUnReadNotifFragmentNotification.setBackgroundColor(resources.getColor(R.color.primary_blue_1))
                cvReadeadNotifFragmentNotification.setCardBackgroundColor(resources.getColor(R.color.background_color_white))
                val unreadNotificationFragment = UnReadNotifFragment()
                val manager = activity?.supportFragmentManager
                val transaction = manager?.beginTransaction()
                transaction?.replace(R.id.fcNotifFragmentNotification,unreadNotificationFragment)
                transaction?.addToBackStack(null)
                transaction?.commit()
            }

            cvReadeadNotifFragmentNotification.setOnClickListener {
                cvUnReadNotifFragmentNotification.setBackgroundColor(resources.getColor(R.color.background_color_white))
                cvReadeadNotifFragmentNotification.setCardBackgroundColor(resources.getColor(R.color.primary_blue_1))
                val readNotificationFragment = ReadNotifFragment()
                val manager = activity?.supportFragmentManager
                val transaction = manager?.beginTransaction()
                transaction?.replace(R.id.fcNotifFragmentNotification,readNotificationFragment)
                transaction?.addToBackStack(null)
                transaction?.commit()
            }
        }
    }

    private fun setBottomNav(){
        val navBar                                     = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility = View.GONE
    }

}