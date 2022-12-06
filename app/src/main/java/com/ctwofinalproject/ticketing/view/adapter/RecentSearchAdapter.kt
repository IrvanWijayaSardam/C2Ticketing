package com.ctwofinalproject.ticketing.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ctwofinalproject.ticketing.databinding.ItemChooseRecentAirportBinding
import com.ctwofinalproject.ticketing.databinding.ItemRecentSearchBinding
import com.ctwofinalproject.ticketing.entity.Airport
import com.ctwofinalproject.ticketing.entity.RecentSearch

class RecentSearchAdapter() : RecyclerView.Adapter<RecentSearchAdapter.ViewHolder>() {
    private lateinit var context : Context
    private lateinit var listener: OnItemClickListener

    fun submitList(list : List<RecentSearch>){
        differ.submitList(list)
    }

    inner class ViewHolder ( val binding : ItemRecentSearchBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onItemClick(differ.currentList[adapterPosition])
            }
        }
    }
    private var diffCallback = object : DiffUtil.ItemCallback<RecentSearch>(){
        override fun areItemsTheSame(
            oldItem: RecentSearch,
            newItem: RecentSearch
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RecentSearch,
            newItem: RecentSearch
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    interface OnItemClickListener{
        fun onItemClick(recentSearch: RecentSearch)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecentSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvAirportCodeFromItemRecentAirport.text = differ.currentList[position].airportCodeFrom
        holder.binding.tvAirportCodeItemToRecentAirport.text = differ.currentList[position].airportCodeTo
        holder.binding.tvClassTicketItemRecentSearch.text = differ.currentList[position].flightClass
        holder.binding.tvDateItemRecentSearch.text = differ.currentList[position].depatureDate
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}