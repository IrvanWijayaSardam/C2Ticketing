package com.ctwofinalproject.ticketing.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ctwofinalproject.ticketing.databinding.ItemBookingBinding
import com.ctwofinalproject.ticketing.databinding.ItemWishlistBinding
import com.ctwofinalproject.ticketing.model.DataItemGetBooking
import com.ctwofinalproject.ticketing.model.DataItemWishlist
import com.ctwofinalproject.ticketing.util.DateConverter
import com.ctwofinalproject.ticketing.util.DecimalSeparator
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions

class MyBookingAdapter(): RecyclerView.Adapter<MyBookingAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var listener: onItemClickListener

    private val diffCallback = object : DiffUtil.ItemCallback<DataItemGetBooking>(){
        override fun areItemsTheSame(oldItem: DataItemGetBooking, newItem: DataItemGetBooking): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItemGetBooking, newItem: DataItemGetBooking): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    fun submitList(dataItemWishlist: List<DataItemGetBooking?>?) = differ.submitList(dataItemWishlist)

    private val differ = AsyncListDiffer(this,diffCallback)

    inner class ViewHolder(val binding: ItemBookingBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onItemClick(differ.currentList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemBookingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = view.root.context
        return ViewHolder(view)
    }

    interface onItemClickListener {
        fun onItemClick(dataItemGetBooking: DataItemGetBooking)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(differ.currentList[position].usersPayment!!.booking!!.ticketIdReturn == null){
            holder.binding.tvTripType.text = "One Way"
            holder.binding.tvDepartureDateItemBookingDeparture.text = differ.currentList[position].usersPayment!!.booking!!.ticketDeparture!!.flight!!.departureDate!!.substring(0,10)
            holder.binding.tvArrivalDateItemBookingDeparture.text = differ.currentList[position].usersPayment!!.booking!!.ticketDeparture!!.flight!!.arrivalDate!!.substring(0,10)
            holder.binding.tvDepartureDateItemBookingReturn.visibility = View.GONE
            holder.binding.tvArrivalDateItemBookingReturn.visibility = View.GONE
        } else {
            holder.binding.tvTripType.text = "Round Trip"
            holder.binding.tvDepartureDateItemBookingDeparture.text = differ.currentList[position].usersPayment!!.booking!!.ticketDeparture!!.flight!!.departureDate!!.substring(0,10)
            holder.binding.tvArrivalDateItemBookingDeparture.text = differ.currentList[position].usersPayment!!.booking!!.ticketDeparture!!.flight!!.arrivalDate!!.substring(0,10)
            holder.binding.tvDepartureDateItemBookingReturn.text = differ.currentList[position].usersPayment!!.booking!!.ticketReturn!!.flight!!.departureDate!!.substring(0,10)
            holder.binding.tvArrivalDateItemBookingReturn.text = differ.currentList[position].usersPayment!!.booking!!.ticketReturn!!.flight!!.arrivalDate!!.substring(0,10)
        }
        if(differ.currentList[position].isPayed == true){
            holder.binding.tvPaymentStatusItemBooking.text = "PAID"
        } else {
            holder.binding.tvPaymentStatusItemBooking.text = "WAITING PAYMENT"
        }
        if(differ.currentList[position].usersPayment!!.booking!!.ticketDeparture!!.classId!!.equals(1)){
            holder.binding.tvFlightClassItemBooking.text = "Businesss"
        } else {
            holder.binding.tvFlightClassItemBooking.text = "Economy"
        }
        holder.binding.tvPlaneNameItemBooking.text = differ.currentList[position].usersPayment!!.booking!!.ticketDeparture!!.flight!!.planeName!!.namePlane
        holder.binding.tvBookingId.setText("Booking Code : ${differ.currentList[position].id.toString()}")
        holder.binding.tvTotalPriceItemBooking.setText("IDR. "+DecimalSeparator.formatDecimalSeperators(differ.currentList[position].totalPrice.toString()))
        holder.binding.tvFlightNumberDepItemBooking.text = differ.currentList[position].usersPayment!!.booking!!.ticketIdDeparture.toString()
        if (differ.currentList[position].usersPayment!!.booking!!.ticketIdReturn != null){
            holder.binding.tvFlightNumberReturnItemBooking.text = differ.currentList[position].usersPayment!!.booking!!.ticketIdReturn.toString()
        } else {
            holder.binding.tvFlightNumberReturnItemBooking.visibility = View.GONE
        }
        holder.binding.tvTotalPassengerItemBooking.setText("Total Passenger : "+differ.currentList[position].usersPayment!!.booking!!.totalPassanger.toString())
//        holder.binding.tvPlaneNameItemBooking.text = differ.currentList[position].usersPayment!!.booking!!.ticketDeparture!!.flight!!.planeName!!.namePlane
        holder.binding.tvUsernameItemBooking.text = differ.currentList[position].usersPayment!!.booking!!.passangerBooking!![0]!!.passanger!!.firstname
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}