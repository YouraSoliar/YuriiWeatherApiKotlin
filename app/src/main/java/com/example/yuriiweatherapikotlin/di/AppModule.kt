package com.example.yuriiweatherapikotlin.di

import android.content.Context
import com.example.yuriiweatherapikotlin.domain.usecase.LoadWeatherUseCase
import com.example.yuriiweatherapikotlin.presentation.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context;
    }

    @Provides
    fun provideMainViewModelFactory(loadWeatherUseCase: LoadWeatherUseCase): MainViewModelFactory {
        return MainViewModelFactory(loadWeatherUseCase)
    }
}