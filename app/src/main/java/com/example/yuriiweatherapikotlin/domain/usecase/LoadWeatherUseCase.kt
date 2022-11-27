package com.example.yuriiweatherapikotlin.domain.usecase

import com.example.yuriiweatherapikotlin.domain.models.City
import com.example.yuriiweatherapikotlin.domain.models.WeatherDay
import com.example.yuriiweatherapikotlin.domain.repository.WeatherRepository

class LoadWeatherUseCase(private val weatherRepository: WeatherRepository) {

    fun execute(city: City): List<WeatherDay> {
        return weatherRepository.loadWeather(city = city)
    }
}