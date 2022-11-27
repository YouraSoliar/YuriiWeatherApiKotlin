package com.example.yuriiweatherapikotlin.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yuriiweatherapikotlin.domain.api.ApiService
import com.example.yuriiweatherapikotlin.domain.models.City
import com.example.yuriiweatherapikotlin.domain.models.WeatherDay
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: ApiService){

    private val weatherDay = MutableLiveData<List<WeatherDay>>()
    private var error = MutableLiveData<Int>()

    val weatherDays: LiveData<List<WeatherDay>>
    get() = weatherDay

    val errors: LiveData<Int>
    get() = error

    fun loadWeather(city: City) {
        val result = apiService.loadWeatherDay(city.city.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                weatherDay.value = it.weatherDaysResponse.weatherDayList
            }) {
                if (city.city == "") {
                    error.value = 3 //fill the field
                } else if (it.message.toString() == "HTTP 400 ") {
                    error.value = 1 //no such city
                } else {
                    error.value = 2 //no internet
                }
            }
    }
}