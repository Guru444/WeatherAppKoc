package com.weather.weatherapp.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weather.weatherapp.R
import com.weather.weatherapp.databinding.WeatherDailyItemLayoutBinding
import com.weather.weatherapp.databinding.WeatherDailyLayoutBinding
import com.weather.weatherapp.databinding.WeatherItemLayoutBinding
import com.weather.weatherapp.model.response.WeatherResponse
import com.weather.weatherapp.util.returnDayList
import com.weather.weatherapp.util.showImage

class WeatherDayListAdapter : ListAdapter<WeatherResponse.WeatherResult, WeatherDayListAdapter.ViewHolder>(
    WeatherDayListDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: WeatherDailyItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WeatherResponse.WeatherResult) {
            with(binding) {
                seekBar.progress = item.clouds?.all ?: 0
                //seekBar.isEnabled = false
                tvDayName.text = returnDayList().get(bindingAdapterPosition)
                tvDegree.text = tvDegree.context.getString(R.string.weather_degree, item.wind?.deg.toString())
                ivIcon.showImage(item.weather?.get(0)?.icon)
                tvDegreeSeekbar.text = item.clouds?.all.toString()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WeatherDailyItemLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class WeatherDayListDiffCallback :
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