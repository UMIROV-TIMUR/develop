package com.umirov.myapplication

import android.app.Application
import com.umirov.myapplication.di.AppComponent
import com.umirov.myapplication.di.DaggerAppComponent

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Создаем компонент
        dagger = DaggerAppComponent.create()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}