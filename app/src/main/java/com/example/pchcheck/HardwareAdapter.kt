package com.example.pchcheck

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HardwareAdapter(private var items: List<HardwareItem>) :
    RecyclerView.Adapter<HardwareAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvItemName)
        val details: TextView = view.findViewById(R.id.tvItemDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hardware, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.name
        holder.details.text = "${item.type} | $${item.price} | Store: ${item.storeName}"
    }

    override fun getItemCount() = items.size

    fun updateList(newList: List<HardwareItem>) {
        items = newList
        notifyDataSetChanged()
    }
}