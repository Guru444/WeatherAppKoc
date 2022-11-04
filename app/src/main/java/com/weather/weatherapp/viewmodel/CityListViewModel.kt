package com.weather.weatherapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.weatherapp.domain.usecase.GetNearbyCitiesUseCase
import com.weather.weatherapp.model.response.NearbyCityResponse
import com.weather.weatherapp.repository.WeatherRepository
import com.weather.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityListViewModel @Inject constructor(
    private val getNearbyCitiesUseCase: GetNearbyCitiesUseCase
)  : ViewModel() {

    private val _nearbyCityListLiveData: MutableLiveData<NearbyCityResponse?> = MutableLiveData()
    val nearbyCityListLiveData: LiveData<NearbyCityResponse?> get() = _nearbyCityListLiveData

    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun nearbyCity(url: String){
        viewModelScope.launch {
            isLoading.value = true
            val result = getNearbyCitiesUseCase.nearbyCities(url)
            when(result){
                is Resource.Success -> {
                    _nearbyCityListLiveData.value = result.data
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