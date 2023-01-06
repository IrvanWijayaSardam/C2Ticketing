package com.ctwofinalproject.ticketing.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ctwofinalproject.ticketing.databinding.ItemTicketTripSummaryBinding
import com.ctwofinalproject.ticketing.model.DataResponseGetTicketById
import com.ctwofinalproject.ticketing.model.ResponseGetTicketById
import com.ctwofinalproject.ticketing.util.DateConverter
import com.ctwofinalproject.ticketing.util.DecimalSeparator
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TicketByIdAdapter():RecyclerView.Adapter<TicketByIdAdapter.ViewHolder>() {
    private lateinit var context : Context

    private val diffCallback = object : DiffUtil.ItemCallback<DataResponseGetTicketById>(){
        override fun areItemsTheSame(
            oldItem: DataResponseGetTicketById,
            newItem: DataResponseGetTicketById
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataResponseGetTicketById,
            newItem: DataResponseGetTicketById
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
    private val differ = AsyncListDiffer(this,diffCallback)


    fun submitList(dataTicketGetById: List<DataResponseGetTicketById>?) = differ.submitList(dataTicketGetById)

    inner class ViewHolder (val binding : ItemTicketTripSummaryBinding ):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemTicketTripSummaryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = view.root.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.binding.tvAirportFromToItemTicketTripSummary.setText(differ.currentList[position].flight!!.departureTerminal!!.code+" to "+differ.currentList[position].flight!!.arrivalTerminal!!.code)
            holder.binding.tvDepartureTimeItemTripSummary.text = differ.currentList[position].flight!!.departureTime.toString()
            holder.binding.tvArrivalTimeItemTripSummary.text = differ.currentList[position].flight!!.arrivalTime.toString()
            holder.binding.tvDepartureDateItemTripSummary.text = differ.currentList[position].flight!!.departureDate.toString().substring(0,9)
            holder.binding.tvArrivalDateItemTripSummary.text = differ.currentList[position].flight!!.arrivalDate.toString().substring(0,9)
            holder.binding.tvAirportCodeFromItemTripSummary.text = differ.currentList[position].flight!!.departureTerminal!!.code.toString()
            holder.binding.tvAirportCodeToItemTripSummary.text = differ.currentList[position].flight!!.arrivalTerminal!!.code.toString()
            holder.binding.tvDepartureDayItemTripSummary.text = DateConverter.convertISOTime(context,differ.currentList[position].flight!!.departureDate.toString())
            holder.binding.tvArrivalDayItemTripSummary.text = DateConverter.convertISOTime(context,differ.currentList[position].flight!!.arrivalDate.toString())
            holder.binding.tvAirplaneNameAndSeriFragmentTripSummaryTicket.text = differ.currentList[position]!!.flight!!.planeName!!.namePlane
            holder.binding.tvPriceTicketItemTicketTripSummary.text = DecimalSeparator.formatDecimalSeperators(differ.currentList[position].price.toString())
    }

    override fun getItemCount(): Int {
        return 1
    }
}