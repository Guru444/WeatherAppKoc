package com.weather.weatherapp.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.weather.weatherapp.model.request.NearbyRequestModel
import com.weather.weatherapp.model.response.AdressInfoCard
import java.util.*
import kotlin.collections.ArrayList


fun urlGenarete(nearbyRequestModel: NearbyRequestModel) : String {
    val urlString = "http://api.geonames.org/findNearbyPostalCodesJSON?postalcode=${nearbyRequestModel.postalCode}&country=TR&radius=${nearbyRequestModel.radius}&username=${nearbyRequestModel.userName}"
    return urlString
}

fun Activity.getLocationCity(
    fusedLocationProviderClient: FusedLocationProviderClient,
    nearbyCityResult: (NearbyRequestModel?) -> Unit = {},
    adressInfoCard: (AdressInfoCard) -> Unit = {}
){
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                val nearbyRequestModel = NearbyRequestModel()
                val adressInfoCard = AdressInfoCard()
                val gcd = Geocoder(this, Locale.getDefault())
                val addresses = gcd.getFromLocation(location.latitude, location.longitude, 1)
                if (addresses.size > 0) {
                    with(adressInfoCard){
                        postalCode = addresses[0].postalCode
                        locationName = addresses[0].subAdminArea
                        latLong = addresses[0].latitude.toString() + addresses[0].longitude.toString()
                    }
                    adressInfoCard(adressInfoCard)

                    nearbyRequestModel.postalCode = addresses[0].postalCode
                    nearbyCityResult(nearbyRequestModel)
                }
            }
        }
    }
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    } else {
        val nwInfo = connectivityManager.activeNetworkInfo ?: return false
        return nwInfo.isConnected
    }
}

fun ImageView.showImage(imgIcon: String?){
    Glide.with(this.context)
        .load("http://openweathermap.org/img/wn/${imgIcon}@2x.png")
        .into(this)
}

fun returnDayList() : ArrayList<String>{
    val days: ArrayList<String> = arrayListOf()
    with(days){
        add("Sun")
        add("Mon")
        add("Tues")
        add("Wednes")
        add("Thurs")
        add("Fri")
        add("Satur")
    }
    return days
}