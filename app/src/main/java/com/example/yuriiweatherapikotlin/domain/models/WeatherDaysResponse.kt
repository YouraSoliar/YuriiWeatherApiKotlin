package com.example.yuriiweatherapikotlin.domain.models

import com.google.gson.annotations.SerializedName


class WeatherDaysResponse(@field:SerializedName("forecastday") val weatherDayList: List<WeatherDay>)