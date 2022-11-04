package com.weather.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weather.weatherapp.databinding.NearbyListItemLayoutBinding
import com.weather.weatherapp.model.response.NearbyCityResponse

class NearbyCityAdapter : ListAdapter<NearbyCityResponse.PostalCode, NearbyCityAdapter.ViewHolder>(
    NearbCityDiffCallback()
    ) {

    var nearbyCityClickListener: NearbyCityClickListener? = null

    interface NearbyCityClickListener {
        fun nearbyCity(item: NearbyCityResponse.PostalCode)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, nearbyCityClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: NearbyListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NearbyCityResponse.PostalCode, nearbyCityClickListener: NearbyCityClickListener?) {
            with(binding) {
                tvNearby.text = item.placeName
                binding.root.setOnClickListener {
                    nearbyCityClickListener?.nearbyCity(item)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NearbyListItemLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class NearbCityDiffCallback :
    DiffUtil.ItemCallback<NearbyCityResponse.PostalCode>() {

    override fun areItemsTheSame(
        oldItem: NearbyCityResponse.PostalCode,
        newItem: NearbyCityResponse.PostalCode
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: NearbyCityResponse.PostalCode,
        newItem: NearbyCityResponse.PostalCode
    ): Boolean {
        return oldItem == newItem
    }
}