package com.mjramirez.openweatherapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert
    suspend fun insert(entry: WeatherEntry)

    @Query("SELECT * FROM weather_entries ORDER BY timestamp DESC")
    fun getAll(): Flow<List<WeatherEntry>>
}

