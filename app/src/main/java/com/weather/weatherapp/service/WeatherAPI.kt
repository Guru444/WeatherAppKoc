package com.weather.weatherapp.service

import com.weather.weatherapp.model.response.NearbyCityResponse
import com.weather.weatherapp.model.response.WeatherResponse
import com.weather.weatherapp.util.Constant
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WeatherAPI {

    companion object{
        const val WEATHER_URL = "data/2.5/forecast"
    }

    @GET
    suspend fun getNearbySearch(@Url placeUrl: String) : NearbyCityResponse

    @GET(WEATHER_URL)
    suspend fun getWeather(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("appid") appid: String = Constant.API_KEY) : WeatherResponse

}