package com.example.pchcheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    // 1. Hardware Data
    private val allHardware = listOf(
        HardwareItem("Intel i9-13900K", "CPU", 580.0, 98, "Store A", 15),
        HardwareItem("NVIDIA RTX 4090", "GPU", 1599.0, 99, "Store B", 25),
        HardwareItem("Corsair Vengeance 32GB", "RAM", 120.0, 85, "Store A", 15),
        HardwareItem("Samsung 990 Pro 1TB", "SSD", 110.0, 92, "Store C", 10)
    )

    private lateinit var adapter: HardwareAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 2. Setup RecyclerView
        val rvHardware = findViewById<RecyclerView>(R.id.rvHardware)
        adapter = HardwareAdapter(allHardware)
        rvHardware.layoutManager = LinearLayoutManager(this)
        rvHardware.adapter = adapter

        // 3. Category Filter Logic
        fun updateDisplay(category: String) {
            val filteredList = allHardware.filter { it.type == category }
            adapter.updateList(filteredList)
            Toast.makeText(this, "Showing $category", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnCPU).setOnClickListener { updateDisplay("CPU") }
        findViewById<Button>(R.id.btnGPU).setOnClickListener { updateDisplay("GPU") }
        findViewById<Button>(R.id.btnRAM).setOnClickListener { updateDisplay("RAM") }
        findViewById<Button>(R.id.btnSSD).setOnClickListener { updateDisplay("SSD") }

        // 4. Logout Logic (Using the Support button for now)
        findViewById<Button>(R.id.btnSupport).apply {
            text = "Logout" // Changing the text so you know what it does
            setOnClickListener {
                Firebase.auth.signOut()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

    // Security Check: If user isn't logged in, kick them back to Login
    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}