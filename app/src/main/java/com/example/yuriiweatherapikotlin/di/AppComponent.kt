package com.example.yuriiweatherapikotlin.di

import com.example.yuriiweatherapikotlin.presentation.MainActivity
import dagger.Component

@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}