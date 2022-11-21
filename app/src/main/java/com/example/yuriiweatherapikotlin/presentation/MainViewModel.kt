package com.example.yuriiweatherapikotlin.presentation

import android.Manifest
import android.R
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yuriiweatherapikotlin.api.ApiFactory
import com.example.yuriiweatherapikotlin.models.City
import com.example.yuriiweatherapikotlin.models.WeatherDay
import com.example.yuriiweatherapikotlin.models.WeatherResponse
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.IOException
import java.util.*


class MainViewModel(@NonNull application: Application?) : AndroidViewModel(application!!) {
    private val weatherDays = MutableLiveData<List<WeatherDay>>()
    private val compositeDisposable = CompositeDisposable()
    private val city = MutableLiveData<City>()
    val weatherDay: LiveData<List<WeatherDay>>
        get() = weatherDays

    fun getCity(): LiveData<City> {
        return city
    }

    val location: Unit
        get() {
            if (ActivityCompat.checkSelfPermission(
                    getApplication(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val fusedLocationProviderClient = LocationServices
                    .getFusedLocationProviderClient(getApplication<Application>())
                fusedLocationProviderClient
                    .lastLocation
                    .addOnCompleteListener(OnCompleteListener<Any?> { task ->
                        val location: Location = task.result
                        if (location != null) {
                            val geocoder = Geocoder(getApplication(), Locale.ENGLISH)
                            try {
                                val addresses: List<Address> = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                                )
                                var yourCity: String = addresses[0].getLocality()
                                if (yourCity.contains("'")) {
                                    yourCity = yourCity.replace("'", "")
                                }
                                city.setValue(City(yourCity))
                                loadWeatherDay(City(yourCity))
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    })
            }
        }

    fun loadWeatherDay(city: City) {
        if (city.city == "") {
            Toast.makeText(getApplication(), R.string.toast_fill_field, Toast.LENGTH_SHORT).show()
        } else {
            val disposable = ApiFactory.apiService.loadWeatherDay(city.city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<WeatherResponse?>() {
                    @Throws(Throwable::class)
                    fun accept(weatherResponse: WeatherResponse) {
                        weatherDays.value = weatherResponse
                            .weatherDaysResponse
                            .weatherDayList
                    }
                }, object : Consumer<Throwable?>() {
                    @Throws(Throwable::class)
                    fun accept(throwable: Throwable) {
                        Log.d(TAG, throwable.message)
                        if (!isNetworkAvailable(getApplication())) {
                            Toast.makeText(
                                getApplication(),
                                R.string.toast_no_internet,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                getApplication(),
                                R.string.toast_no_city,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            compositeDisposable.add(disposable)
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager
            .activeNetworkInfo!!.isConnected
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    companion object {
        private const val TAG = "WeatherAPI"
    }
}