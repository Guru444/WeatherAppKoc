package com.weather.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weather.weatherapp.R
import com.weather.weatherapp.databinding.WeatherItemLayoutBinding
import com.weather.weatherapp.model.response.WeatherResponse
import com.weather.weatherapp.util.showImage

class WeatherListAdapter : ListAdapter<WeatherResponse.WeatherResult, WeatherListAdapter.ViewHolder>(
    WeatherListDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: WeatherItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WeatherResponse.WeatherResult) {
            with(binding) {
                item.dtTxt?.split(" ")?.last()?.let {
                   tvTime.text = tvTime.context.getString(R.string.weather_time, it.substring(0, it.indexOf(":")))
                }
                tvDegree.text = tvDegree.context.getString(R.string.weather_degree, item.wind?.deg.toString())
                ivIcon.showImage(item.weather?.get(0)?.icon)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WeatherItemLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class WeatherListDiffCallback :
    DiffUtil.ItemCallback<WeatherResponse.WeatherResult>() {

    override fun areItemsTheSame(
        oldItem: WeatherResponse.WeatherResult,
        newItem: WeatherResponse.WeatherResult
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: WeatherResponse.WeatherResult,
        newItem: WeatherResponse.WeatherResult
    ): Boolean {
        return oldItem == newItem
    }
}