package com.weather.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.weatherapp.databinding.WeatherCurrentLayoutBinding
import com.weather.weatherapp.databinding.WeatherDailyLayoutBinding
import com.weather.weatherapp.databinding.WeatherHourlyLayoutBinding
import com.weather.weatherapp.model.MainContentData
import com.weather.weatherapp.model.response.WeatherResponse
import com.weather.weatherapp.util.Constant

class WeatherAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val WEATHER_CURRENT = 0
        const val WEATHER_HOURLY = 1
        const val WEATHER_DAYS = 2
        const val WEATHER_INFO = 3
    }

    var weatherContentList: ArrayList<MainContentData> = arrayListOf(
        MainContentData(WEATHER_CURRENT, mapOf()),
        MainContentData(WEATHER_HOURLY, mapOf()),
        MainContentData(WEATHER_DAYS, mapOf()),
        MainContentData(WEATHER_INFO, mapOf()))


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return when (viewType){
           WEATHER_CURRENT -> {
               WeatherCurrentViewHolder(WeatherCurrentLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
           }
           WEATHER_HOURLY -> {
               WeatherHourlyViewHolder(WeatherHourlyLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
           }
           WEATHER_DAYS -> {
               WeatherDaysViewHolder(WeatherDailyLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
           }

           else -> {
               WeatherCurrentViewHolder(WeatherCurrentLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
           }
       }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (weatherContentList[position].viewType == WEATHER_CURRENT) {
            (holder as WeatherCurrentViewHolder).bind()
        }
        if (weatherContentList[position].viewType == WEATHER_HOURLY) {
            (holder as WeatherHourlyViewHolder).bind()
        }
        if (weatherContentList[position].viewType == WEATHER_DAYS) {
            (holder as WeatherDaysViewHolder).bind()
        }
    }

    inner class WeatherCurrentViewHolder(private val itemBinding: WeatherCurrentLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(){
            itemBinding.apply {
                val weatherCurrentData = weatherContentList.find { it.viewType == WEATHER_CURRENT }?.data
                if (weatherCurrentData.isNullOrEmpty().not()){
                    val data = weatherCurrentData?.get(Constant.WEATHER_CURRENT) as WeatherResponse
                    data.let {
                        with(it){
                            tvLocationName.text = city?.name
                            tvDegree.text = list?.getOrNull(0)?.wind?.deg.toString()
                            tvWeatherDesc.text = list?.getOrNull(0)?.weather?.getOrNull(0)?.description
                        }
                    }
                }
            }
        }
    }

    inner class WeatherHourlyViewHolder(private val itemBinding: WeatherHourlyLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(){
            itemBinding.apply {
                val weatherHourlyData = weatherContentList.find { it.viewType == WEATHER_HOURLY }?.data
                val weatherListAdapter = WeatherListAdapter()
                rvWeatherHourlyList.adapter = weatherListAdapter
                if (weatherHourlyData.isNullOrEmpty().not()){
                    val data = weatherHourlyData?.get(Constant.WEATHER_HOURLY) as WeatherResponse
                    data.let {
                        weatherListAdapter.submitList(it.list)
                    }
                }
            }
        }
    }

    inner class WeatherDaysViewHolder(private val itemBinding: WeatherDailyLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(){
            itemBinding.apply {
                val weatherDailyData = weatherContentList.find { it.viewType == WEATHER_DAYS }?.data
                val weatherDayListAdapter = WeatherDayListAdapter()
                rvWeatherDailyList.adapter = weatherDayListAdapter
                if (weatherDailyData.isNullOrEmpty().not()){
                    val data = weatherDailyData?.get(Constant.WEATHER_DAILY) as WeatherResponse
                    data.let { dataItem->
                        dataItem.list?.let {
                            val tempList: ArrayList<WeatherResponse.WeatherResult> = arrayListOf()
                            for (x in 0 until 7){
                                tempList.add(it[x])
                            }
                            weatherDayListAdapter.submitList(tempList)
                        }
                    }
                }
            }
        }
    }

    fun setWeatherCurrentItem(weatherCurrent: MainContentData) {
        weatherContentList.find { it.viewType == WEATHER_CURRENT }?.data = weatherCurrent.data
        notifyItemChanged(WEATHER_CURRENT)
    }

    fun setHourlyItem(weatherHourlyCurrent: MainContentData) {
        weatherContentList.find { it.viewType == WEATHER_HOURLY }?.data = weatherHourlyCurrent.data
        notifyItemChanged(WEATHER_HOURLY)
    }
    fun setDailyItem(weatherDailyCurrent: MainContentData) {
        weatherContentList.find { it.viewType == WEATHER_DAYS }?.data = weatherDailyCurrent.data
        notifyItemChanged(WEATHER_DAYS)
    }

    override fun getItemViewType(position: Int): Int {
        return weatherContentList[position].viewType
    }

    override fun getItemCount(): Int = weatherContentList.size
}
