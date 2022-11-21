package com.example.yuriiweatherapikotlin.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yuriiweatherapikotlin.api.ApiFactory
import com.example.yuriiweatherapikotlin.models.City
import com.example.yuriiweatherapikotlin.models.WeatherDay
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel : ViewModel() {
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
                }, {
                    error.value = 1 //no such city
                })
            compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}