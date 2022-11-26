package com.example.yuriiweatherapikotlin.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yuriiweatherapikotlin.api.ApiFactory
import com.example.yuriiweatherapikotlin.models.City
import com.example.yuriiweatherapikotlin.models.WeatherDay
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

open class MainViewModel : ViewModel() {
    val city: MutableLiveData<City> by lazy { MutableLiveData<City>() }
    private val weatherDays = MutableLiveData<List<WeatherDay>>()
    private val compositeDisposable = CompositeDisposable()
    private var error = MutableLiveData<Int>()

    fun getWeatherDay(): LiveData<List<WeatherDay>> {
        return weatherDays
    }

    fun getError(): LiveData<Int> {
        return error
    }

    fun loadWeatherDay(city: City) {
            val disposable = ApiFactory.apiService.loadWeatherDay(city.city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    weatherDays.value = it.weatherDaysResponse.weatherDayList
                }) {
                    if (city.city == "") {
                        error.value = 3 //fill the field
                    } else if (it.message.toString() == "HTTP 400 ") {
                        error.value = 1 //no such city
                    } else {
                        error.value = 2 //no internet
                    }
                }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}