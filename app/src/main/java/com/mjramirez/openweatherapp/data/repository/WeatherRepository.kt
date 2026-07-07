package com.mjramirez.openweatherapp.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.mjramirez.openweatherapp.R
import com.mjramirez.openweatherapp.data.api.OpenWeatherService
import com.mjramirez.openweatherapp.data.api.WeatherResponse
import com.mjramirez.openweatherapp.data.db.AppDatabase
import com.mjramirez.openweatherapp.data.db.WeatherEntry
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository(private val context: Context) {
    private val db = AppDatabase.get(context)
    private val service: OpenWeatherService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherService::class.java)
    }
    private val fused: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    fun history(): Flow<List<WeatherEntry>> = db.weatherDao().getAll()

    suspend fun fetchAndSaveCurrentWeather(): WeatherResponse = withContext(Dispatchers.IO) {
        val loc = getLastLocation() ?: Location("").apply {
            // fallback to Manila
            latitude = 14.5995
            longitude = 120.9842
        }
        val response = service.currentWeather(loc.latitude, loc.longitude, context.resources.getString(
            R.string.weather_api_key))
        val city = response.name
        val country = response.sys?.country ?: ""
        val temp = response.main?.temp ?: 0f
        val condition = response.weather.firstOrNull()?.main ?: "Clear"
        db.weatherDao().insert(
            WeatherEntry(
                timestamp = System.currentTimeMillis(),
                city = city,
                country = country,
                tempC = temp,
                condition = condition
            )
        )
        response
    }

    @SuppressLint("MissingPermission")
    private suspend fun getLastLocation(): Location? = try {
        fused.lastLocation.await()
    } catch (e: Exception) {
        null
    }
}

