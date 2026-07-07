package com.mjramirez.openweatherapp.ui.presenter

import android.content.Context
import com.mjramirez.openweatherapp.data.repository.WeatherRepository

class WeatherPresenter(context: Context, private var view: WeatherContract.View?) : WeatherContract.Presenter {
    private val repo = WeatherRepository(context)

    override suspend fun loadCurrentWeather() {
        try {
            view?.showLoading(true)
            val data = repo.fetchAndSaveCurrentWeather()
            view?.showCurrentWeather(data)
        } catch (e: Exception) {
            view?.showError(e.message ?: "Unknown error")
        } finally {
            view?.showLoading(false)
        }
    }

    override fun onDestroy() {
        view = null
    }
}

