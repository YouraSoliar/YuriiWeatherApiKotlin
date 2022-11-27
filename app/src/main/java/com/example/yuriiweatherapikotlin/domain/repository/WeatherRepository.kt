package com.example.yuriiweatherapikotlin.domain.repository

import com.example.yuriiweatherapikotlin.domain.models.City
import com.example.yuriiweatherapikotlin.domain.models.WeatherDay

interface WeatherRepository {
    fun loadWeather(city: City): List<WeatherDay>
}