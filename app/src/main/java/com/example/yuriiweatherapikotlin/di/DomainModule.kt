package com.example.yuriiweatherapikotlin.di

import com.example.yuriiweatherapikotlin.domain.repository.WeatherRepository
import com.example.yuriiweatherapikotlin.domain.usecase.LoadWeatherUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideLoadWeatherUseCase(weatherRepository: WeatherRepository): LoadWeatherUseCase{
        return LoadWeatherUseCase(weatherRepository)
    }

}