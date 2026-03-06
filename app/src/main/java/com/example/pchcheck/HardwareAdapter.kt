package com.example.pchcheck

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HardwareAdapter(private var hardwareList: List<HardwareItem>) :
    RecyclerView.Adapter<HardwareAdapter.ViewHolder>() {

    // 1. The ViewHolder: Links the code to the XML IDs
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // These IDs must match exactly what is in your item_hardware.xml
        val tvName: TextView = view.findViewById(R.id.tvHardwareName)
        val tvPrice: TextView = view.findViewById(R.id.tvHardwarePrice)
        val tvStore: TextView = view.findViewById(R.id.tvStoreName)
    }

    // 2. Inflate the layout for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hardware, parent, false)
        return ViewHolder(view)
    }

    // 3. Bind the data to the views
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = hardwareList[position]

        holder.tvName.text = item.name
        holder.tvPrice.text = "$${item.price}" // Adds the dollar sign
        holder.tvStore.text = item.store       // Shows "Amazon" or "Micro Center"
    }

    // 4. Return the size of the list
    override fun getItemCount(): Int {
        return hardwareList.size
    }

    /**
     * This function is crucial!
     * It allows the Search Bar in MainActivity to send a
     * filtered list here and refresh the screen.
     */
    fun updateList(newList: List<HardwareItem>) {
        this.hardwareList = newList
        notifyDataSetChanged() // Tells the list to redraw itself
    }
}