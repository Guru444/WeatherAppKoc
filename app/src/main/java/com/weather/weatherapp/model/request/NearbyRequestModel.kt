package com.weather.weatherapp.model.request

import com.weather.weatherapp.util.Constant

data class NearbyRequestModel(
        var postalCode: String? = null,
        var country: String? = Constant.COUNTRY_TR,
        var radius: Int? = 10,
        var userName: String? = Constant.USER_NAME
)
