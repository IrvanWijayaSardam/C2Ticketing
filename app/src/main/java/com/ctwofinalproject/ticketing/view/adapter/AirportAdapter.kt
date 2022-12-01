package com.ctwofinalproject.ticketing.view.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ctwofinalproject.ticketing.R
import com.ctwofinalproject.ticketing.databinding.ItemChooseAllAirportBinding
import com.ctwofinalproject.ticketing.model.DataItem
import com.ctwofinalproject.ticketing.model.ResponseAirport

class AirportAdapter():RecyclerView.Adapter<AirportAdapter.ViewHolder>() {
    private lateinit var context : Context
    private lateinit var listener: onItemClickListener

    private val diffCallback = object : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitList(dataitem : List<DataItem>?) = differ.submitList(dataitem)

    fun setOnItemClickListener(listener: onItemClickListener){
        this.listener = listener
    }

    interface onItemClickListener {
        fun onItemClick(airportData: DataItem)
    }

    inner class ViewHolder ( val binding : ItemChooseAllAirportBinding ):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onItemClick(differ.currentList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemChooseAllAirportBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvCityCountryItemChooseAllAirport.setText(differ.currentList[position].city + "," + differ.currentList[position].country)
        holder.binding.tvAirportItemChooseAllAirport.text = differ.currentList[position].name
        holder.binding.tvAirportCodeItemChooseAirport.text = differ.currentList[position].code
        /*
        holder.binding.llLocationChooseAllAirport.setOnClickListener {
            var bund = Bundle()
            bund.putString("code",holder.binding.tvAirportCodeItemChooseAirport.text.toString())
            bund.putString("city",holder.binding.tvCityCountryItemChooseAllAirport.text.toString())
            bund.putString("airport_name",holder.binding.tvAirportItemChooseAllAirport.text.toString())
            bund.putString("requestCode",fromto)
            Navigation.findNavController(holder.itemView).navigate(R.id.action_airportFragment_to_homeFragment,bund)
            Log.d(TAG, "onClick: code ${holder.binding.tvAirportCodeItemChooseAirport.text}")
        }
         */
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}