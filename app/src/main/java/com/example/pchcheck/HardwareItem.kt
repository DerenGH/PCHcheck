package com.example.pchcheck

data class HardwareItem(
    val name: String,
    val type: String,
    val price: Double,
    val condition: Int,
    val store: String,      // This is the String it was looking for
    val imageResId: Int     // This is the Int (like R.drawable.logo)
)