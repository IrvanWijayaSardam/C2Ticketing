package com.ctwofinalproject.ticketing.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ctwofinalproject.ticketing.data.Passenger
import com.ctwofinalproject.ticketing.databinding.ItemPassengerBinding

class PassengerListAdapter(val passengerAmmount: Int): RecyclerView.Adapter<PassengerListAdapter.ViewHolder>() {
    private lateinit var listener: OnItemClickListener

    fun submitList(list : List<Passenger?>){
        differ.submitList(list)
    }

    fun modifyItemList(list: List<Passenger?>,position: Int){
        differ.submitList(list)
        notifyItemChanged(position)
    }

    inner class ViewHolder(val binding: ItemPassengerBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if(differ.currentList.isNotEmpty() && differ.currentList.size != adapterPosition){
                    listener.onItemClick(differ.currentList[adapterPosition],adapterPosition)
                } else {
                    listener.onItemClick(null,adapterPosition)
                }
            }
        }
    }

    private var diffCallback = object : DiffUtil.ItemCallback<Passenger>(){
        override fun areItemsTheSame(
            oldItem: Passenger,
            newItem: Passenger
        ): Boolean {
            return oldItem.nik == newItem.nik
        }

        override fun areContentsTheSame(
            oldItem: Passenger,
            newItem: Passenger
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPassengerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (differ.currentList.isNotEmpty() && differ.currentList.size != holder.adapterPosition){
            holder.binding.tvNameItemPassenger.setText(differ.currentList[position].firstname+" "+differ.currentList[position].lastname)
            holder.binding.tvNikItemPassenger.text = differ.currentList[position].nik.toString()
        } else {
            holder.binding.tvNameItemPassenger.setText("Passenger ${position+1}")
            holder.binding.tvNikItemPassenger.text = "NIK"
        }
    }

    override fun getItemCount(): Int {
        return passengerAmmount
    }

    interface OnItemClickListener{
        fun onItemClick(passenger: Passenger?,position: Int)
    }
}