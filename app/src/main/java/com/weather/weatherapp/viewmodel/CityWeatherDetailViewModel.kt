package com.weather.weatherapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.weatherapp.model.response.NearbyCityResponse
import com.weather.weatherapp.model.response.WeatherResponse
import com.weather.weatherapp.repository.WeatherRepository
import com.weather.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityWeatherDetailViewModel  @Inject constructor(private val repository: WeatherRepository)  : ViewModel(){

    val weatherResponseLiveData = MutableLiveData<WeatherResponse?>()
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun nearbyCity(lat: Double, long: Double){
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.weatherData(lat, long)
            when(result){
                is Resource.Success -> {
                    weatherResponseLiveData.value = result.data
                    errorMessage.value = ""
                    isLoading.value = false
                }
                is Resource.Error -> {
                    errorMessage.value = result.message.toString()
                    isLoading.value = false
                }
                else -> {}
            }
        }
    }
}