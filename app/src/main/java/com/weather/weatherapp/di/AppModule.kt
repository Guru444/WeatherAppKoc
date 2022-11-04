package com.weather.weatherapp.di

import com.weather.weatherapp.repository.WeatherRepository
import com.weather.weatherapp.service.WeatherAPI
import com.weather.weatherapp.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun weatherRepository(api: WeatherAPI) = WeatherRepository(api)

    @Singleton
    @Provides
    fun provideWeatherApi() : WeatherAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constant.BASE_URL)
            .build()
            .create(WeatherAPI::class.java)
    }

}