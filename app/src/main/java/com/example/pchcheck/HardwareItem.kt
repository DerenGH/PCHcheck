package com.example.pchcheck

data class HardwareItem(
    val name: String,
    val type: String,
    val price: Double,
    val performanceScore: Int,
    val storeName: String,
    val travelTimeMinutes: Int
)