package com.ctwofinalproject.ticketing.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ctwofinalproject.ticketing.databinding.ItemBookingBinding
import com.ctwofinalproject.ticketing.model.DataItemGetBooking
import com.ctwofinalproject.ticketing.model.DataItemHistory
import com.ctwofinalproject.ticketing.util.DecimalSeparator

class MyBookingUpcomingAdapter(): RecyclerView.Adapter<MyBookingUpcomingAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var listener: onItemClickListener

    private val diffCallback = object : DiffUtil.ItemCallback<DataItemHistory>(){
        override fun areItemsTheSame(oldItem: DataItemHistory, newItem: DataItemHistory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItemHistory, newItem: DataItemHistory): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    fun submitList(dataItemHistory: List<DataItemHistory?>?) = differ.submitList(dataItemHistory)

    private val differ = AsyncListDiffer(this,diffCallback)

    inner class ViewHolder(val binding: ItemBookingBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onItemClick(differ.currentList[adapterPosition])
            }
        }
    }


    interface onItemClickListener {
        fun onItemClick(dataItemGetBooking: DataItemHistory)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemBookingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = view.root.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var totalPrice          = 0
        var totalPriceDeparture = 0
        var totalPriceReturn    = 0

        holder.binding.tvUsernameItemBooking.setText(differ.currentList[position].userBooking!!.users!!.firstname.toString() + differ.currentList[position].userBooking!!.users!!.lastname.toString())
        holder.binding.tvPlaneNameItemBooking.visibility = View.GONE
        holder.binding.tvBookingId.setText("BOOKING CODE : "+differ.currentList[position].id)
        holder.binding.tvDepartureDateItemBookingDeparture.text = differ.currentList[position].userBooking!!.booking!!.ticketDeparture!!.createdAt.toString().substring(0,9)
        totalPriceDeparture = (differ.currentList[position].userBooking!!.booking!!.ticketDeparture!!.price!!.toInt() * differ.currentList[position].userBooking!!.booking!!.passangerBooking!!.size)

        if(differ.currentList[position].userBooking!!.booking!!.ticketDeparture!!.classId!!.equals(1)) {
            holder.binding.tvFlightClassItemBooking.text = "Business"
            totalPriceReturn = (differ.currentList[position].userBooking!!.booking!!.ticketReturn!!.price!!.toInt() * differ.currentList[position].userBooking!!.booking!!.passangerBooking!!.size)
        } else {
            holder.binding.tvFlightClassItemBooking.text = "Economy"
        }
        if(differ.currentList[position].userBooking!!.booking!!.ticketIdReturn != null){
            holder.binding.tvTripType.text = "Round Trip"
        }
        totalPrice = totalPriceDeparture + totalPriceReturn
        holder.binding.tvTotalPriceItemBooking.setText("IDR "+DecimalSeparator.formatDecimalSeperators(totalPrice.toString()))
        holder.binding.tvFlightNumberDepItemBooking.text = differ.currentList[position].userBooking!!.booking!!.ticketIdDeparture.toString()
        holder.binding.tvTotalPassengerItemBooking.text = differ.currentList[position].userBooking!!.booking!!.passangerBooking!!.size.toString()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}