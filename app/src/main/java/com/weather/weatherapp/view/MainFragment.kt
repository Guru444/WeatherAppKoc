package com.weather.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.weather.weatherapp.R
import com.weather.weatherapp.databinding.FragmentMainBinding
import com.weather.weatherapp.util.getLocationCity
import com.weather.weatherapp.util.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.apply {
            requireActivity().getLocationCity(fusedLocationProviderClient, {}, {
                it.let {
                    tvLocationName.text = getString(R.string.location_name, it.locationName)
                    tvPostalcode.text = getString(R.string.postal_code, it.postalCode)
                    tvLatLong.text = getString(R.string.lat_long, it.latLong)
                    tvNetworkStatus.text = getString(R.string.network_state, isNetworkAvailable(requireContext()).toString())
                }
            })
        }
    }
}