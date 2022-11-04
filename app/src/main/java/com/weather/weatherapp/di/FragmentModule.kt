package com.weather.weatherapp.di

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.weather.weatherapp.adapter.NearbyCityAdapter
import com.weather.weatherapp.adapter.WeatherListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    fun provideLinearLayoutManager(@ApplicationContext context: Context): LinearLayoutManager {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        linearLayoutManager.stackFromEnd = true
        return linearLayoutManager
    }

    @Provides
    fun provideAdapter(): NearbyCityAdapter {
        return NearbyCityAdapter()
    }

    @Provides
    fun provideWeatherAdapter(): WeatherListAdapter {
        return WeatherListAdapter()
    }

    @Provides
    fun locationProvideAdapter(@ApplicationContext context: Context): FusedLocationProviderClient {
        return FusedLocationProviderClient(context)
    }

}