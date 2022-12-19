package com.ctwofinalproject.ticketing.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ctwofinalproject.ticketing.databinding.ItemShowTicketBinding
import com.ctwofinalproject.ticketing.databinding.ItemWishlistBinding
import com.ctwofinalproject.ticketing.model.DataItemFlight
import com.ctwofinalproject.ticketing.model.DataItemWishlist

class WishlistAdapter(): RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<DataItemWishlist>(){
        override fun areItemsTheSame(oldItem: DataItemWishlist, newItem: DataItemWishlist): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItemWishlist, newItem: DataItemWishlist): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    fun submitList(dataItemWishlist: List<DataItemWishlist?>?) = differ.submitList(dataItemWishlist)

    private val differ = AsyncListDiffer(this,diffCallback)

    inner class ViewHolder(val binding: ItemWishlistBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemWishlistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(differ.currentList[position].ticketReturn == null){
            holder.binding.tvTripType.text = "One Way"
        } else {
            holder.binding.tvTripType.text = "Round Trip"
        }
        holder.binding.tvAirportFromToItemTicketTripSummary.setText("FLIGHT FROM "+"${differ.currentList[position].ticketDeparture!!.flightId}"+
        " TO " + differ.currentList[position].ticketDeparture!!.country)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}