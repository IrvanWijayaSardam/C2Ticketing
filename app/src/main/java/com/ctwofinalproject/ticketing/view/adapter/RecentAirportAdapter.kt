package com.ctwofinalproject.ticketing.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ctwofinalproject.ticketing.databinding.ItemChooseAllAirportBinding
import com.ctwofinalproject.ticketing.databinding.ItemChooseRecentAirportBinding
import com.ctwofinalproject.ticketing.entity.Airport
import com.ctwofinalproject.ticketing.model.DataItem

class RecentAirportAdapter() : RecyclerView.Adapter<RecentAirportAdapter.ViewHolder>() {
    private lateinit var context : Context
    private lateinit var listener: OnItemClickListener


    fun submitList(list : List<Airport>){
        differ.submitList(list)
    }

    private var diffCallback = object : DiffUtil.ItemCallback<Airport>(){
        override fun areItemsTheSame(
            oldItem: Airport,
            newItem: Airport
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Airport,
            newItem: Airport
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    interface OnItemClickListener{
        fun onItemClick(airport: Airport)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    inner class ViewHolder ( val binding : ItemChooseRecentAirportBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onItemClick(differ.currentList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChooseRecentAirportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvCityCountryItemChooseRecentAirport.setText(differ.currentList[position].city + "," +differ.currentList[position].country)
        holder.binding.tvAirportItemChooseRecentAirport.text = differ.currentList[position].airportName
        holder.binding.tvAirportCodeItemChooseAirport.text = differ.currentList[position].airportCode
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}