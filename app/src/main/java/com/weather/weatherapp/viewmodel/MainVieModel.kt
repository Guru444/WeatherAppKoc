package com.weather.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import com.weather.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainVieModel @Inject constructor(private val repository: WeatherRepository)  : ViewModel(){

}