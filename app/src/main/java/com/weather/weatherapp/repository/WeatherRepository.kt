package com.weather.weatherapp.repository

import com.weather.weatherapp.model.response.NearbyCityResponse
import com.weather.weatherapp.model.response.WeatherResponse
import com.weather.weatherapp.service.WeatherAPI
import com.weather.weatherapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class WeatherRepository @Inject constructor(private val api: WeatherAPI){

    suspend fun getNearbyCities(url: String) : Resource<NearbyCityResponse> {
        val response = try {
            api.getNearbySearch(url)
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun weatherData(lat: Double, long: Double) : Resource<WeatherResponse> {
        val response = try {
            api.getWeather(lat, long)
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

}