package com.example.yuriiweatherapikotlin.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.yuriiweatherapikotlin.R
import com.example.yuriiweatherapikotlin.adapter.WeatherAdapter
import com.example.yuriiweatherapikotlin.databinding.ActivityMainBinding
import com.example.yuriiweatherapikotlin.models.City
import com.example.yuriiweatherapikotlin.models.WeatherDay


class MainActivity : AppCompatActivity() {
    private var adapter: WeatherAdapter? = null
    private var viewModel: MainViewModel? = null
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.getRoot()
        setContentView(view)
        initView()
        initAction()
    }

    fun initView() {
        adapter = WeatherAdapter()
        binding.recyclerViewWeather.setAdapter(adapter)
        supportActionBar
            .setBackgroundDrawable(
                ColorDrawable(
                    resources
                        .getColor(R.color.orange)
                )
            )
    }

    fun initAction() {
        viewModel.getCity().observe(this, object : Observer<City?>() {
            fun onChanged(city: City) {
                binding.editTextCity.setText(city.city)
            }
        })
        viewModel.getWeatherDay().observe(this, object : Observer<List<WeatherDay?>?>() {
            fun onChanged(weatherDays: List<WeatherDay?>?) {
                adapter!!.setWeathers(weatherDays)
            }
        })
        binding.textViewFind.setOnClickListener(object : OnClickListener() {
            fun onClick(view: View?) {
                viewModel.loadWeatherDay(City(binding.editTextCity.getText().toString()))
            }
        })
        binding.textViewLocation.setOnClickListener(object : OnClickListener() {
            fun onClick(view: View?) {
                val isPermission = checkPermission()
                if (isPermission) {
                    viewModel.getLocation()
                }
            }
        })
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
}