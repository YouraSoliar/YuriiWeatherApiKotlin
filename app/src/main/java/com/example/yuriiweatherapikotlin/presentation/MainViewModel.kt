package com.example.yuriiweatherapikotlin.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yuriiweatherapikotlin.domain.models.City
import com.example.yuriiweatherapikotlin.domain.models.WeatherDay
import com.example.yuriiweatherapikotlin.domain.repository.WeatherRepository
import kotlinx.coroutines.launch

open class MainViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    val city: MutableLiveData<City> by lazy { MutableLiveData<City>() }
    val weathersLiveData: LiveData<List<WeatherDay>>
    get() = weatherRepository.weatherDays

    val errorsLiveData: LiveData<Int>
    get() = weatherRepository.errors

    fun loadWeatherDay(city: City) {
        viewModelScope.launch {
            weatherRepository.loadWeather(city)
        }
    }
}