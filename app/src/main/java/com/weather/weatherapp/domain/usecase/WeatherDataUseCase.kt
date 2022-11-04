package com.weather.weatherapp.domain.usecase

import com.weather.weatherapp.model.response.WeatherResponse
import com.weather.weatherapp.repository.WeatherRepository
import com.weather.weatherapp.util.Resource
import javax.inject.Inject

class WeatherDataUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend fun weatherData(lat: Double, long: Double) : Resource<WeatherResponse> = weatherRepository.weatherData(lat, long)
}