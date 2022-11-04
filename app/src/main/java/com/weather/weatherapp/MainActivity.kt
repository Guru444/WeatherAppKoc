package com.weather.weatherapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.weather.weatherapp.databinding.ActivityMainBinding
import com.weather.weatherapp.util.checkLocationPermission
import com.weather.weatherapp.util.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            this@MainActivity.checkLocationPermission()

            if (isNetworkAvailable(this@MainActivity).not()){
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("İnternet Bağlantı Hatası")
                    .setMessage("İnternet Bağlantınızı Kontrol edin")
                    .setCancelable(false)
                    .setPositiveButton("Tamam", object : DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            p0?.cancel()
                            finish()
                        }
                    }).create().show()
            }
            val navController = findNavController(R.id.fragment_btm_nav)
            NavigationUI.setupWithNavController(btmNav,navController)

            navController.addOnDestinationChangedListener { _, destination, arguments ->
                btmNav.apply {
                    when (destination.id) {
                        R.id.cityWeatherDetailFragment -> visibility = View.GONE
                        else -> visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}