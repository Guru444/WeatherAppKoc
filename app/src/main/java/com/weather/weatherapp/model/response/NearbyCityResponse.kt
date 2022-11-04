package com.weather.weatherapp.model.response


import com.google.gson.annotations.SerializedName

class NearbyCityResponse{
    @SerializedName("postalCodes")
    var postalCodes: ArrayList<PostalCode>? = null

    data class PostalCode(
        @SerializedName("countryCode")
        var countryCode: String? = null,
        @SerializedName("distance")
        var distance: String? = null,
        @SerializedName("lat")
        var lat: Double? = null,
        @SerializedName("lng")
        var lng: Double? = null,
        @SerializedName("placeName")
        var placeName: String? = null,
        @SerializedName("postalCode")
        var postalCode: String? = null
    )
}