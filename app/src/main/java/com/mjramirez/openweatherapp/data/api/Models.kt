package com.mjramirez.openweatherapp.data.api

data class WeatherResponse(
    val name: String,
    val sys: Sys?,
    val main: Main?,
    val weather: List<Weather> = emptyList()
)

data class Sys(
    val country: String?,
    val sunrise: Long?,
    val sunset: Long?
)

data class Main(
    val temp: Float?
)

data class Weather(
    val id: Int,
    val main: String?,
    val description: String?
)

