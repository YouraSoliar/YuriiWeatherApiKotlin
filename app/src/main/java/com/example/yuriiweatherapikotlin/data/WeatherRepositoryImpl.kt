package com.example.yuriiweatherapikotlin.data

import android.content.Context
import com.example.yuriiweatherapikotlin.domain.api.ApiFactory
import com.example.yuriiweatherapikotlin.domain.models.City
import com.example.yuriiweatherapikotlin.domain.models.Temperature
import com.example.yuriiweatherapikotlin.domain.models.WeatherDay
import com.example.yuriiweatherapikotlin.domain.repository.WeatherRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherRepositoryImpl(private val context: Context): WeatherRepository {
    override fun loadWeather(city: City): List<WeatherDay> {

//        ApiFactory.apiService.loadWeatherDay(city.city)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                weatherDays.value = it.weatherDaysResponse.weatherDayList
//            }) {
//                if (city.city == "") {
//                    error.value = 3 //fill the field
//                } else if (it.message.toString() == "HTTP 400 ") {
//                    error.value = 1 //no such city
//                } else {
//                    error.value = 2 //no internet
//                }
//            }



       val temp = Temperature("d", "d", "s", 1, 1)
        val weathe = WeatherDay("dd", temp)
        val lits= mutableListOf<WeatherDay>(weathe, weathe)
        return lits
    }


}