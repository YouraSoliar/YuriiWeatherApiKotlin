package com.example.yuriiweatherapikotlin.api

import com.example.yuriiweatherapikotlin.models.WeatherResponse
import io.reactivex.rxjava3.core.Single

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("forecast.json?key=7a56f2fc25654cf1aaa180014221611&days=14")
    fun loadWeatherDay(@Query("q") city: String = ""): Single<WeatherResponse>
}