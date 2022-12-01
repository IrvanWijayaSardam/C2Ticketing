package com.ctwofinalproject.ticketing.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ctwofinalproject.ticketing.databinding.ItemChooseAllAirportBinding
import com.ctwofinalproject.ticketing.model.ResponseAirportItem

class AirportAdapter(private var airportData : List<ResponseAirportItem>):RecyclerView.Adapter<AirportAdapter.ViewHolder>() {
    private lateinit var context : Context

    class ViewHolder ( val binding : ItemChooseAllAirportBinding ):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemChooseAllAirportBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvCityCountryItemChooseAllAirport.setText(airportData[position].city + "," + airportData[position].country)
        holder.binding.tvAirportItemChooseAllAirport.text = airportData[position].name
        holder.binding.tvAirportCodeItemChooseAirport.text = airportData[position].code
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun getItemCount(): Int {
        return airportData.size
    }
}