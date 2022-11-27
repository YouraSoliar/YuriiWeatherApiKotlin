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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.yuriiweatherapikotlin.R
import com.example.yuriiweatherapikotlin.app.App
import com.example.yuriiweatherapikotlin.databinding.ActivityMainBinding
import com.example.yuriiweatherapikotlin.domain.models.City
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @javax.inject.Inject
    lateinit var vmFactory: MainViewModelFactory

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, vmFactory)[MainViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
        initAction()
    }

    private fun initView() {
        supportActionBar
            ?.setBackgroundDrawable(
                ColorDrawable(
                    resources
                        .getColor(R.color.orange)
                )
            )
    }

    private fun initAction() {
        viewModel.errorsLiveData.observe(this) {
            when (it) {
                1 -> {
                    Toast.makeText(application, R.string.toast_no_city, Toast.LENGTH_SHORT).show()
                }
                2 -> {
                    Toast.makeText(application, R.string.toast_no_internet, Toast.LENGTH_SHORT).show()
                }
                3 -> {
                    Toast.makeText(application, R.string.toast_fill_field, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.city.observe(this) {
            binding.editTextCity.setText(it.city)
            viewModel.loadWeatherDay(it)
        }

        binding.textViewFind.setOnClickListener {
            val city = City(binding.editTextCity.text.toString())
            if (!isNetworkAvailable()) {
                Toast.makeText(application, R.string.toast_no_internet, Toast.LENGTH_SHORT).show()
            } else {
                viewModel.loadWeatherDay(city)
                openFragment(FragmentWeatherList.newInstance())
            }
        }

        binding.textViewLocation.setOnClickListener {
            getLocation()
        }

        binding.textViewWeatherList.setOnClickListener {
            openFragment(FragmentWeatherList.newInstance())
        }

        binding.textViewConstance.setOnClickListener {
            openFragment(FragmentConstance.newInstance())
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
                        openFragment(FragmentWeatherList.newInstance())
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.place_holder, fragment).commit()
    }
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null
                && connectivityManager.activeNetworkInfo!!.isConnected
    }
}
