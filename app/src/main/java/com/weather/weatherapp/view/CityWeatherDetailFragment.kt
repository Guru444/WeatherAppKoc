package com.weather.weatherapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.weather.weatherapp.adapter.WeatherAdapter
import com.weather.weatherapp.adapter.WeatherListAdapter
import com.weather.weatherapp.databinding.FragmentCityWeatherDetailBinding
import com.weather.weatherapp.model.MainContentData
import com.weather.weatherapp.model.response.WeatherResponse
import com.weather.weatherapp.util.Constant
import com.weather.weatherapp.viewmodel.CityWeatherDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CityWeatherDetailFragment : Fragment() {

    private lateinit var binding: FragmentCityWeatherDetailBinding
    private val cityWeatherDetailViewModel: CityWeatherDetailViewModel by viewModels()
    private var lat: Double? = null
    private var lon: Double? = null

    private var weatherAdapter = WeatherAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCityWeatherDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            (arguments)?.let {
               lat = it.get(Constant.LAT) as Double
               lon = it.get(Constant.LONG) as Double
            }
            lat?.let { lat->
                lon?.let {  lon->
                    cityWeatherDetailViewModel.nearbyCity(lat,lon)
                }
            }

            rvWeather.adapter = weatherAdapter

            cityWeatherDetailViewModel.weatherResponseLiveData.observe(viewLifecycleOwner){
                it?.let { weather->
                    weatherAdapter.setWeatherCurrentItem(MainContentData(WeatherAdapter.WEATHER_CURRENT, mapOf(Constant.WEATHER_CURRENT to weather)))
                    weatherAdapter.setHourlyItem(MainContentData(WeatherAdapter.WEATHER_HOURLY, mapOf(Constant.WEATHER_HOURLY to weather)))
                    weatherAdapter.setDailyItem(MainContentData(WeatherAdapter.WEATHER_DAYS, mapOf(Constant.WEATHER_DAILY to weather)))
                }
            }
        }
    }
}