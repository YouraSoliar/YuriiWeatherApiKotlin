package com.example.yuriiweatherapikotlin.domain.models

import com.google.gson.annotations.SerializedName


class WeatherDay(@field:SerializedName("date") val date: String, temperature: Temperature) {

    @SerializedName("day")
    private val temperature: Temperature
    fun getTemperature(): Temperature {
        return temperature
    }

    init {
        this.temperature = temperature
    }
}