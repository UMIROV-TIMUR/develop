package com.umirov.myapplication

import android.app.Application
import com.umirov.myapplication.di.AppComponent
import com.umirov.myapplication.di.DaggerAppComponent
import com.umirov.myapplication.di.modules.DatabaseModule
import com.umirov.myapplication.di.modules.DomainModule
import com.umirov.myapplication.di.modules.RemoteModule

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Создаем компонент
        dagger =
            DaggerAppComponent.builder()
                .remoteModule(RemoteModule())
                .databaseModule(DatabaseModule())
                .domainModule(DomainModule(this))
                .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}