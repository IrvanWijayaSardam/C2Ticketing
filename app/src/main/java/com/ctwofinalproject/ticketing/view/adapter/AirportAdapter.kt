package com.ctwofinalproject.ticketing.view.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.ItemChooseAllAirportBinding
import com.ctwofinalproject.ticketing.model.ResponseAirport

class AirportAdapter(private var airportData: ResponseAirport, private var fromto: String):RecyclerView.Adapter<AirportAdapter.ViewHolder>() {
    private lateinit var context : Context

    class ViewHolder ( val binding : ItemChooseAllAirportBinding ):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemChooseAllAirportBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvCityCountryItemChooseAllAirport.setText(airportData.data!![position]!!.city + "," + airportData.data!![position]!!.country)
        holder.binding.tvAirportItemChooseAllAirport.text = airportData.data!![position]!!.name
        holder.binding.tvAirportCodeItemChooseAirport.text = airportData.data!![position]!!.code
        holder.binding.llLocationChooseAllAirport.setOnClickListener {
            var bund = Bundle()
            bund.putString("code",holder.binding.tvAirportCodeItemChooseAirport.text.toString())
            bund.putString("city",holder.binding.tvCityCountryItemChooseAllAirport.text.toString())
            bund.putString("airport_name",holder.binding.tvAirportItemChooseAllAirport.text.toString())
            bund.putString("requestCode",fromto)
            Navigation.findNavController(holder.itemView).navigate(R.id.action_airportFragment_to_homeFragment,bund)
            Log.d(TAG, "onClick: code ${holder.binding.tvAirportCodeItemChooseAirport.text}")
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun getItemCount(): Int {
        return airportData.data!!.size
    }
}