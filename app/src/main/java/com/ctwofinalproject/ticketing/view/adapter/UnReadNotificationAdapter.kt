package com.ctwofinalproject.ticketing.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ctwofinalproject.ticketing.databinding.ItemNotificationBinding
import com.ctwofinalproject.ticketing.model.DataItemNotification

class UnReadNotificationAdapter(): RecyclerView.Adapter<UnReadNotificationAdapter.ViewHolder>() {

    private lateinit var context : Context
    private lateinit var listener: onItemClickListener

    private val diffCallback = object : DiffUtil.ItemCallback<DataItemNotification>() {
        override fun areItemsTheSame(oldItem: DataItemNotification, newItem: DataItemNotification): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItemNotification, newItem: DataItemNotification): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitList(dataitem: List<DataItemNotification?>?) = differ.submitList(dataitem)

    fun setOnItemClickListener(listener: onItemClickListener){
        this.listener = listener
    }

    interface onItemClickListener {
        fun onItemClick(notificationData : DataItemNotification)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class ViewHolder ( val binding : ItemNotificationBinding ):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onItemClick(differ.currentList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnReadNotificationAdapter.ViewHolder {
        val view = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = view.root.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvStatusItemNotif.text = differ.currentList[position].message
    }

}