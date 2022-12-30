package com.ctwofinalproject.ticketing.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ctwofinalproject.ticketing.databinding.ItemPassengerBinding
import com.ctwofinalproject.ticketing.model.PassangerBookingItemGetBooking

class PassengerDetailsAdapter(val passengerAmmount: Int): RecyclerView.Adapter<PassengerDetailsAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<PassangerBookingItemGetBooking>() {
        override fun areItemsTheSame(oldItem: PassangerBookingItemGetBooking, newItem: PassangerBookingItemGetBooking): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PassangerBookingItemGetBooking, newItem: PassangerBookingItemGetBooking): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitList(dataitem: List<PassangerBookingItemGetBooking?>?) = differ.submitList(dataitem)


    inner class ViewHolder(val binding: ItemPassengerBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemPassengerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvNameItemPassenger.setText("Mr / Mrs : "+ differ.currentList[position].passanger!!.firstname+" "+differ.currentList[position].passanger!!.lastname)
        holder.binding.tvNikItemPassenger.setText("No Id : "+differ.currentList[position].passanger!!.identityNumber)

    }

    override fun getItemCount(): Int {
        return passengerAmmount
    }
}