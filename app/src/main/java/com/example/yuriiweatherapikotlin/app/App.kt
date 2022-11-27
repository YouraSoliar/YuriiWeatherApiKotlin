package com.example.yuriiweatherapikotlin.app

import android.app.Application
import com.example.yuriiweatherapikotlin.di.AppComponent
import com.example.yuriiweatherapikotlin.di.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().build()
    }
}