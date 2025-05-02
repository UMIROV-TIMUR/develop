package com.umirov.myapplication

import android.app.Application
import com.umirov.myapplication.di.AppComponent
import com.umirov.myapplication.di.DaggerAppComponent
import com.umirov.remote_module.DaggerRemoteComponent
import com.umirov.myapplication.di.modules.DatabaseModule
import com.umirov.myapplication.di.modules.DomainModule

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Создаем компонент
        val remoteProvider = DaggerRemoteComponent.create()
        dagger =
            DaggerAppComponent.builder()
                .remoteProvider(remoteProvider)
                .databaseModule(DatabaseModule())
                .domainModule(DomainModule(this))
                .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}