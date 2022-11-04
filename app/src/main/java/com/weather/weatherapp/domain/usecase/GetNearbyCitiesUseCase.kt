package com.weather.weatherapp.domain.usecase

import com.weather.weatherapp.model.response.NearbyCityResponse
import com.weather.weatherapp.repository.WeatherRepository
import com.weather.weatherapp.util.Resource
import javax.inject.Inject

class GetNearbyCitiesUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    suspend fun nearbyCities(url: String) : Resource<NearbyCityResponse> = weatherRepository.getNearbyCities(url)
}