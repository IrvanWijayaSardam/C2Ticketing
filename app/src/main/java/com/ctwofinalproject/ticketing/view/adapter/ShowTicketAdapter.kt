package com.ctwofinalproject.ticketing.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ctwofinalproject.ticketing.databinding.ItemShowTicketBinding
import com.ctwofinalproject.ticketing.model.DataItem
import com.ctwofinalproject.ticketing.model.DataItemFlight

class ShowTicketAdapter(): RecyclerView.Adapter<ShowTicketAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var listener: onItemClickListener

    private val diffCallback = object : DiffUtil.ItemCallback<DataItemFlight>(){
        override fun areItemsTheSame(oldItem: DataItemFlight, newItem: DataItemFlight): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItemFlight, newItem: DataItemFlight): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    fun submitList(dataItemFlight: List<DataItemFlight?>) = differ.submitList(dataItemFlight)

    private val differ = AsyncListDiffer(this,diffCallback)

    inner class ViewHolder(val binding: ItemShowTicketBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onItemClick(differ.currentList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemShowTicketBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvNameClassFixTicketFragmentShowTicket.text = differ.currentList[position].jsonMemberClass!!.type
        holder.binding.tvDepTimeItemShowTicket.text = differ.currentList[position].flight!!.departureTime
        holder.binding.tvArrTimeItemShowTicket.text = differ.currentList[position].flight!!.arrivalTime
        holder.binding.tvFlightNumberItemShowTicket.text = differ.currentList[position].flightId.toString()
        holder.binding.tvPriceItemShowTicket.setText("IDR "+differ.currentList[position].price.toString())
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface onItemClickListener {
        fun onItemClick(dataItemFlight: DataItemFlight)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        this.listener = listener
    }

}