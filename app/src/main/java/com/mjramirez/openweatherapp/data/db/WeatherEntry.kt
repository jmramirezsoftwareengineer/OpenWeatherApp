package com.mjramirez.openweatherapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_entries")
data class WeatherEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val city: String,
    val country: String,
    val tempC: Float,
    val condition: String
)

