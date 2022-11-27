package com.example.yuriiweatherapikotlin.di

import android.content.Context
import com.example.yuriiweatherapikotlin.data.WeatherRepositoryImpl
import com.example.yuriiweatherapikotlin.domain.repository.WeatherRepository
import com.example.yuriiweatherapikotlin.domain.usecase.LoadWeatherUseCase
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideWeatherRepository(context: Context): WeatherRepository {
        return WeatherRepositoryImpl(context = context)
    }
}