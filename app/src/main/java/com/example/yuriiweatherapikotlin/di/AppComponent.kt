package com.example.yuriiweatherapikotlin.di

import com.example.yuriiweatherapikotlin.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}