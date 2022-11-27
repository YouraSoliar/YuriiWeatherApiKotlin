package com.example.yuriiweatherapikotlin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yuriiweatherapikotlin.domain.repository.WeatherRepository
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val weatherRepository: WeatherRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(weatherRepository) as T    }

}