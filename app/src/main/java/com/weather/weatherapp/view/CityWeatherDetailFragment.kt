package com.weather.weatherapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.weather.weatherapp.adapter.WeatherListAdapter
import com.weather.weatherapp.databinding.FragmentCityWeatherDetailBinding
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

    @Inject
    lateinit var weatherListAdapter: WeatherListAdapter

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

            rvWeatherList.adapter = weatherListAdapter

            cityWeatherDetailViewModel.weatherResponseLiveData.observe(viewLifecycleOwner){
                it?.let {
                    tvLocationName.text = it.city?.name
                    tvDeegree.text = it.list?.getOrNull(0)?.wind?.deg.toString()
                    tvWeatherDesc.text = it.list?.getOrNull(0)?.weather?.getOrNull(0)?.description
                    weatherListAdapter.submitList(it.list)
                }
            }
        }
    }
}