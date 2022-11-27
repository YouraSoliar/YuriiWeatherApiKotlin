package com.example.yuriiweatherapikotlin.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yuriiweatherapikotlin.domain.usecase.LoadWeatherUseCase

class MainViewModelFactory(val loadWeatherUseCase: LoadWeatherUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(loadWeatherUseCase = loadWeatherUseCase) as T    }

}