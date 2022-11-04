package com.weather.weatherapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.weatherapp.domain.usecase.WeatherDataUseCase
import com.weather.weatherapp.model.response.WeatherResponse
import com.weather.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityWeatherDetailViewModel  @Inject constructor(
    private val weatherDataUseCase: WeatherDataUseCase
)  : ViewModel(){

    private val _weatherResponseLiveData: MutableLiveData<WeatherResponse?> = MutableLiveData()
    val weatherResponseLiveData: LiveData<WeatherResponse?> get() = _weatherResponseLiveData


    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun nearbyCity(lat: Double, long: Double){
        viewModelScope.launch {
            isLoading.value = true
            val result = weatherDataUseCase.weatherData(lat, long)
            when(result){
                is Resource.Success -> {
                    _weatherResponseLiveData.value = result.data
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