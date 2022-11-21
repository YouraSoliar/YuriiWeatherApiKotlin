package com.example.yuriiweatherapikotlin.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.yuriiweatherapikotlin.R
import com.example.yuriiweatherapikotlin.adapter.WeatherAdapter
import com.example.yuriiweatherapikotlin.databinding.ActivityMainBinding
import com.example.yuriiweatherapikotlin.models.City
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: WeatherAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
        initAction()
    }

    private fun initView() {
        adapter = WeatherAdapter()
        binding.recyclerViewWeather.adapter = adapter
        supportActionBar
            ?.setBackgroundDrawable(
                ColorDrawable(
                    resources
                        .getColor(R.color.orange)
                )
            )
    }

    private fun initAction() {
        viewModel.getError().observe(this) {
            if (it == 1) {
                Toast.makeText(application, R.string.toast_no_city, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getWeatherDay().observe(this) {
            adapter.setWeathers(it)
        }

        binding.textViewFind.setOnClickListener {
            val city = City(binding.editTextCity.text.toString())
            if (!isNetworkAvailable()) {
                Toast.makeText(application, R.string.toast_fill_field, Toast.LENGTH_SHORT).show()
            } else if ((city.city == "")) {
                Toast.makeText(application, R.string.toast_no_internet, Toast.LENGTH_SHORT).show()
            } else {
                viewModel.loadWeatherDay(city)
            }
        }

        binding.textViewLocation.setOnClickListener {
            getLocation()
        }
    }

    private fun checkPermission(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                11
            )
            false
        } else {
            true
        }
    }

    private fun getLocation() {
        val isPermission = checkPermission()
        if (isPermission) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val geocoder = Geocoder(application, Locale.ENGLISH)
                    try {
                        val addresses: List<Address> = geocoder.getFromLocation(
                            location.latitude, location.longitude, 1
                        )
                        val city = City(addresses[0].locality)
                        if (city.city.contains("'")) {
                            city.city = city.city.replace("'", "")
                        }
                        binding.editTextCity.setText(city.city)
                        viewModel.loadWeatherDay(city)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null
                && connectivityManager.activeNetworkInfo!!.isConnected
    }
}
