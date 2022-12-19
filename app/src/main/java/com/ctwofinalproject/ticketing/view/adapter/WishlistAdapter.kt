package com.ctwofinalproject.ticketing.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ctwofinalproject.ticketing.databinding.ItemShowTicketBinding
import com.ctwofinalproject.ticketing.databinding.ItemWishlistBinding
import com.ctwofinalproject.ticketing.model.DataItemFlight
import com.ctwofinalproject.ticketing.model.DataItemWishlist
import com.ctwofinalproject.ticketing.util.DateConverter

class WishlistAdapter(): RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var listener: onItemClickListener

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
        init {
            binding.root.setOnClickListener {
                listener.onItemClick(differ.currentList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemWishlistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = view.root.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(differ.currentList[position].ticketIdReturn == null){
            holder.binding.tvTripType.text = "Round Trip"
        } else {
            holder.binding.tvTripType.text = "One Way"
        }
        holder.binding.tvAirportFromToItemTicketTripSummary.setText("FLIGHT FROM "+"${differ.currentList[position].ticketDeparture!!.flight!!.departureTerminal!!.code}"+
        " TO " + differ.currentList[position].ticketDeparture!!.flight!!.arrivalTerminal!!.code)
        holder.binding.tvDepartureTimeItemWishlist.text = differ.currentList[position].ticketDeparture!!.flight!!.departureTime
        holder.binding.tvArrivalTimeItemItemWishlist.text = differ.currentList[position].ticketDeparture!!.flight!!.arrivalTime
        holder.binding.tvDepartureDateItemItemWishlist.text = differ.currentList[position].ticketDeparture!!.flight!!.departureDate
        holder.binding.tvArrivalDateItemWishlist.text = differ.currentList[position].ticketDeparture!!.flight!!.arrivalTime
        holder.binding.tvDepartureDayItemItemWishlist.text = DateConverter.convertISOTime(context,differ.currentList[position].ticketDeparture!!.flight!!.departureDate.toString())
        holder.binding.tvArrivalDayItemWishlist.text = DateConverter.convertISOTime(context,differ.currentList[position].ticketDeparture!!.flight!!.arrivalDate.toString())
        holder.binding.tvAirportCodeFromItemWishlist.text = differ.currentList[position].ticketDeparture!!.flight!!.departureTerminal!!.code
        holder.binding.tvAirportCodeToItemItemWishlist.text = differ.currentList[position].ticketDeparture!!.flight!!.arrivalTerminal!!.code
    }

    interface onItemClickListener {
        fun onItemClick(dataItemWishlist: DataItemWishlist)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}