package com.weather.weatherapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi


class NetworkMonitoringUtil(context: Context) : ConnectivityManager.NetworkCallback() {

    private var mNetworkRequest: NetworkRequest? = null
    private var mConnectivityManager: ConnectivityManager? = null

   init {
       mNetworkRequest = NetworkRequest.Builder()
           .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
           .build()
       mConnectivityManager =
           context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
   }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        Log.d("Network State", "onAvailable() called: Connected to network")
        mNetworkRequest
    }
    override fun onLost(network: Network) {
        super.onLost(network)
        Log.e("Network State", "onLost() called: Lost network connection");
    }


    fun registerNetworkCallbackEvents() {
        Log.d("Network State", "registerNetworkCallbackEvents() called")
        mConnectivityManager?.registerNetworkCallback(mNetworkRequest!!, this)
    }


}

