package com.ctwofinalproject.ticketing.view.ui.mytrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentBookingBinding
import com.ctwofinalproject.ticketing.databinding.FragmentMyTripBinding


class MyTripFragment : Fragment() {
    private var _binding: FragmentMyTripBinding?                  = null
    private val binding get()                                     = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyTripBinding.inflate(inflater,container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding?.run {
            cvPastFragmentMyTrip.setCardBackgroundColor(resources.getColor(R.color.primary_blue_1))

            cvPastFragmentMyTrip.setOnClickListener {
                cvPastFragmentMyTrip.setCardBackgroundColor(resources.getColor(R.color.primary_blue_1))
                cvUpComingFragmentMyTrip.setCardBackgroundColor(resources.getColor(R.color.secondary_font_color))
                cvBookingFragmentMyTrip.setCardBackgroundColor(resources.getColor(R.color.secondary_font_color))
                val selectMyPastFragment = MyPastBookingFragment()
                val manager = activity?.supportFragmentManager
                val transaction = manager?.beginTransaction()
                transaction?.replace(R.id.fcMyTripFragmentMyTrip,selectMyPastFragment)
                transaction?.addToBackStack(null)
                transaction?.commit()
            }
            cvUpComingFragmentMyTrip.setOnClickListener {
                cvPastFragmentMyTrip.setCardBackgroundColor(resources.getColor(R.color.secondary_font_color))
                cvUpComingFragmentMyTrip.setCardBackgroundColor(resources.getColor(R.color.primary_blue_1))
                cvBookingFragmentMyTrip.setCardBackgroundColor(resources.getColor(R.color.secondary_font_color))
                val selectMyUpcomingFragment = MyUpcomingBookingFragment()
                val manager = activity?.supportFragmentManager
                val transaction = manager?.beginTransaction()
                transaction?.replace(R.id.fcMyTripFragmentMyTrip,selectMyUpcomingFragment)
                transaction?.addToBackStack(null)
                transaction?.commit()
            }
            cvBookingFragmentMyTrip.setOnClickListener {
                cvPastFragmentMyTrip.setCardBackgroundColor(resources.getColor(R.color.secondary_font_color))
                cvUpComingFragmentMyTrip.setCardBackgroundColor(resources.getColor(R.color.secondary_font_color))
                cvBookingFragmentMyTrip.setCardBackgroundColor(resources.getColor(R.color.primary_blue_1))
                val selectMyBooking = MyBookingFragment()
                val manager = activity?.supportFragmentManager
                val transaction = manager?.beginTransaction()
                transaction?.replace(R.id.fcMyTripFragmentMyTrip,selectMyBooking)
                transaction?.addToBackStack(null)
                transaction?.commit()
            }
        }
    }


}