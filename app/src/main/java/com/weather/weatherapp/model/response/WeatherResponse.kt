package com.weather.weatherapp.model.response


import com.google.gson.annotations.SerializedName

class WeatherResponse{
    @SerializedName("city")
    var city: City? = null
    @SerializedName("cnt")
    var cnt: Int? = null
    @SerializedName("cod")
    var cod: String? = null
    @SerializedName("list")
    var list: ArrayList<WeatherResult>? = null
    @SerializedName("message")
    var message: Int? = null

    data class City(
        @SerializedName("coord")
        var coord: Coord? = null,
        @SerializedName("country")
        var country: String? = null,
        @SerializedName("id")
        var id: Int? = null,
        @SerializedName("name")
        var name: String? = null,
        @SerializedName("population")
        var population: Int? = null,
        @SerializedName("sunrise")
        var sunrise: Int? = null,
        @SerializedName("sunset")
        var sunset: Int? = null,
        @SerializedName("timezone")
        var timezone: Int? = null
    ) {
        data class Coord(
            @SerializedName("lat")
            var lat: Double? = null,
            @SerializedName("lon")
            var lon: Double? = null
        )
    }

    data class WeatherResult (
        @SerializedName("clouds")
        var clouds: Clouds? = null,
        @SerializedName("dt")
        var dt: Int? = null,
        @SerializedName("dt_txt")
        var dtTxt: String? = null,
        @SerializedName("main")
        var main: Main? = null,
        @SerializedName("pop")
        var pop: Double? = null,
        @SerializedName("rain")
        var rain: Rain? = null,
        @SerializedName("sys")
        var sys: Sys? = null,
        @SerializedName("visibility")
        var visibility: Int? = null,
        @SerializedName("weather")
        var weather: ArrayList<Weather>? = null,
        @SerializedName("wind")
        var wind: Wind? = null
    ) {
        data class Clouds(
            @SerializedName("all")
            var all: Int? = null
        )

        data class Main(
            @SerializedName("feels_like")
            var feelsLike: Double? = null,
            @SerializedName("grnd_level")
            var grndLevel: Int? = null,
            @SerializedName("humidity")
            var humidity: Int? = null,
            @SerializedName("pressure")
            var pressure: Int? = null,
            @SerializedName("sea_level")
            var seaLevel: Int? = null,
            @SerializedName("temp")
            var temp: Double? = null,
            @SerializedName("temp_kf")
            var tempKf: Double? = null,
            @SerializedName("temp_max")
            var tempMax: Double? = null,
            @SerializedName("temp_min")
            var tempMin: Double? = null
        )

        data class Rain(
            @SerializedName("3h")
            var h: Double? = null
        )

        data class Sys(
            @SerializedName("pod")
            var pod: String? = null
        )

        data class Weather(
            @SerializedName("description")
            var description: String? = null,
            @SerializedName("icon")
            var icon: String? = null,
            @SerializedName("id")
            var id: Int? = null,
            @SerializedName("main")
            var main: String? = null
        )

        data class Wind(
            @SerializedName("deg")
            var deg: Int? = null,
            @SerializedName("gust")
            var gust: Double? = null,
            @SerializedName("speed")
            var speed: Double? = null
        )
    }
}