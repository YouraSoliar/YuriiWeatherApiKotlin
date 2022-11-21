package com.example.yuriiweatherapikotlin.models

import com.google.gson.annotations.SerializedName

class Temperature(
    @field:SerializedName("maxtemp_c") val maxTemperature: String,
    @field:SerializedName("mintemp_c") val minTemperature: String,
    @field:SerializedName("avgtemp_c") val avgTemperature: String,
    @field:SerializedName("daily_will_it_rain") val isRain: Int,
    @field:SerializedName("daily_will_it_snow") val isSnow: Int
)