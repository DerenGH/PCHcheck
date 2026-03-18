package com.example.pchcheck

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private val allHardware = listOf(
        HardwareItem("Intel i9-13900K", "CPU", 580.0, 98, "Amazon", 0),
        HardwareItem("NVIDIA RTX 4090", "GPU", 1599.0, 99, "Amazon", 0),
        HardwareItem("Corsair Vengeance 32GB", "RAM", 120.0, 85, "Micro Center", 0),
        HardwareItem("Samsung 990 Pro 1TB", "SSD", 110.0, 92, "Amazon", 0),
        HardwareItem("AMD Ryzen 7 7800X3D", "CPU", 449.0, 95, "Micro Center", 0)
    )

    private lateinit var adapter: HardwareAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find UI elements
        val rvHardware = findViewById<RecyclerView>(R.id.rvHardware)
        val searchView = findViewById<SearchView>(R.id.searchView)
        val etMin = findViewById<EditText>(R.id.etMinPrice)
        val etMax = findViewById<EditText>(R.id.etMaxPrice)
        val cbAmazon = findViewById<CheckBox>(R.id.cbAmazon)
        val cbMicro = findViewById<CheckBox>(R.id.cbMicroCenter)
        val btnLogout = findViewById<Button>(R.id.btnSupport)

        // --- THE FONT COLOR FIX ---
        val searchText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchText.setTextColor(Color.parseColor("#333333")) // Solid Gray Text
        searchText.setHintTextColor(Color.parseColor("#888888")) // Readable Hint
        // --------------------------

        // Setup RecyclerView
        adapter = HardwareAdapter(allHardware)
        rvHardware.layoutManager = LinearLayoutManager(this)
        rvHardware.adapter = adapter

        // Master Filter Function
        fun applyFilters() {
            val query = searchView.query.toString().lowercase()
            val min = etMin.text.toString().toDoubleOrNull() ?: 0.0
            val max = etMax.text.toString().toDoubleOrNull() ?: 10000.0

            val filtered = allHardware.filter { item ->
                val matchesSearch = item.name.lowercase().contains(query)
                val matchesPrice = item.price in min..max

                val noStoreSelected = !cbAmazon.isChecked && !cbMicro.isChecked
                val matchesStore = noStoreSelected ||
                        (cbAmazon.isChecked && item.store == "Amazon") ||
                        (cbMicro.isChecked && item.store == "Micro Center")

                matchesSearch && matchesPrice && matchesStore
            }
            adapter.updateList(filtered)
        }

        // Listeners
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String?): Boolean { applyFilters(); return true }
            override fun onQueryTextChange(q: String?): Boolean { applyFilters(); return true }
        })

        cbAmazon.setOnCheckedChangeListener { _, _ -> applyFilters() }
        cbMicro.setOnCheckedChangeListener { _, _ -> applyFilters() }

        // Logout
        btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}