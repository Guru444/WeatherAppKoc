package com.weather.weatherapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.weather.weatherapp.R
import com.weather.weatherapp.adapter.NearbyCityAdapter
import com.weather.weatherapp.analytics.AnalyticsTools
import com.weather.weatherapp.databinding.FragmentCityBinding
import com.weather.weatherapp.model.response.NearbyCityResponse
import com.weather.weatherapp.util.Constant
import com.weather.weatherapp.util.getLocationCity
import com.weather.weatherapp.util.urlGenarete
import com.weather.weatherapp.viewmodel.CityListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CityFragment : Fragment() {

    private lateinit var binding: FragmentCityBinding
    private val cityListViewModel: CityListViewModel by viewModels()

    @Inject
    lateinit var nearbyCityAdapter: NearbyCityAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        nearbyCityAdapter = NearbyCityAdapter()

        val rvNearbyCity = object : NearbyCityAdapter.NearbyCityClickListener{
            override fun nearbyCity(item: NearbyCityResponse.PostalCode) {
                AnalyticsTools.logCustomEvent(Constant.NEARBY_CITY_CLICK, bundleOf(Constant.NEARBY_ID to item.placeName))
                Navigation.findNavController(view).navigate(R.id.action_cityFragment_to_cityWeatherDetailFragment, bundleOf(Constant.LAT to item.lat, Constant.LONG to item.lng))
            }
        }

        binding.apply {

            rvNearbyList.adapter = nearbyCityAdapter
            nearbyCityAdapter.nearbyCityClickListener = rvNearbyCity

            requireActivity().getLocationCity(fusedLocationProviderClient, {
                    it?.let {
                        cityListViewModel.nearbyCity(urlGenarete(it))
                    }
               },{}
            )

            cityListViewModel.nearbyCityList.observe(viewLifecycleOwner){
                it?.let {
                    nearbyCityAdapter.submitList(it.postalCodes)
                }
            }
        }
    }
}