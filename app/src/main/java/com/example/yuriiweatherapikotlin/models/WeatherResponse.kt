package com.example.yuriiweatherapikotlin.models

import com.google.gson.annotations.SerializedName


class WeatherResponse(@field:SerializedName("forecast") val weatherDaysResponse: WeatherDaysResponse)