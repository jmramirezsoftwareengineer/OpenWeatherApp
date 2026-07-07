package com.mjramirez.openweatherapp.ui.presenter

import com.mjramirez.openweatherapp.data.api.WeatherResponse

interface WeatherContract {
    interface View {
        fun showLoading(show: Boolean)
        fun showCurrentWeather(data: WeatherResponse?)
        fun showError(message: String)
    }
    interface Presenter {
        suspend fun loadCurrentWeather()
        fun onDestroy()
    }
}

