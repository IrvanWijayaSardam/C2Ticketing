package com.ctwofinalproject.ticketing.view.ui.wishlist

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.FragmentLoginBinding
import com.ctwofinalproject.ticketing.databinding.FragmentWishlistBinding
import com.ctwofinalproject.ticketing.model.DataItemWishlist
import com.ctwofinalproject.ticketing.view.adapter.ShowTicketAdapter
import com.ctwofinalproject.ticketing.view.adapter.WishlistAdapter
import com.ctwofinalproject.ticketing.viewmodel.ProtoViewModel
import com.ctwofinalproject.ticketing.viewmodel.WishlistViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistFragment : Fragment() {
    private var _binding: FragmentWishlistBinding?                 = null
    private val binding get()                                      = _binding!!
    lateinit var adapterWishlist                                   : WishlistAdapter
    val viewModelWishlist                                          : WishlistViewModel by viewModels()
    private val viewModelProto                                     : ProtoViewModel by viewModels()
    var token : String                                             = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWishlistBinding.inflate(inflater,container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterWishlist                                          = WishlistAdapter()
        initListener()
        setBottomNav()

        viewModelProto.dataUser.observe(viewLifecycleOwner){
            if(it != null){
                token = it.token
                viewModelWishlist.getWishlist("bearer "+token)
                Log.d(TAG, "onViewCreated: ${token}")
            }
        }

        viewModelWishlist.liveDataWishlist.observe(viewLifecycleOwner){
            if(it != null){
                adapterWishlist.submitList(it.data)
                binding.rvWishlistFWishlist.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                binding.rvWishlistFWishlist.adapter = adapterWishlist
                binding.shimmerBarWishlist.visibility = View.GONE
                binding.ivEmptyListFWishlist.visibility = View.GONE
                binding.tvEmptyListFWishlist.visibility = View.GONE
            } else {
                Log.d(TAG, "onViewCreated: Belum ada wishlist")
                binding.shimmerBarWishlist.visibility = View.GONE
            }
        }

        adapterWishlist.setOnItemClickListener(object : WishlistAdapter.onItemClickListener{
            override fun onItemClick(dataItemWishlist: DataItemWishlist) {
                if(dataItemWishlist.ticketIdReturn == null){
                    viewModelProto.deleteTicketIdDeparture()
                    viewModelProto.submitTicketIdDeparture(dataItemWishlist.ticketIdDeparture.toString())
                    Navigation.findNavController(requireView()).navigate(R.id.action_wishlistFragment_to_tripSummaryPassengerFragment)
                } else {
                    viewModelProto.deleteTicketIdDeparture()
                    viewModelProto.deleteTicketIdReturn()
                    viewModelProto.submitTicketIdDeparture(dataItemWishlist.ticketIdDeparture.toString())
                    viewModelProto.submitTicketIdReturn(dataItemWishlist.ticketIdReturn.toString())
                    Navigation.findNavController(requireView()).navigate(R.id.action_wishlistFragment_to_tripSummaryPassengerFragment)
                }
            }
        })
    }

    private fun initListener() {
        binding?.run {
            ivBackFWishlist.setOnClickListener {
                Navigation.findNavController(binding.root).popBackStack()
            }
        }
    }

    private fun setBottomNav(){
        val navBar                                     = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        navBar?.visibility                             = View.GONE
    }
}