package com.umirov.myapplication

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.umirov.myapplication.di.AppComponent
import com.umirov.myapplication.di.DaggerAppComponent
import com.umirov.myapplication.di.modules.DatabaseModule
import com.umirov.myapplication.di.modules.DomainModule
import com.umirov.myapplication.view.notifications.NotificationConstants.CHANNEL_ID
import com.umirov.remote_module.DaggerRemoteComponent

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Задаем имя, оиписание и важность канала
            val name = "WatchLaterChannel"
            val descriptionText = "FilmSearch notification Channel "
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            //Создаем канал, передав в парамемры его ID(строка), имя(строка), важность(константа)
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            //Отдельно задаём описание
            mChannel.description = descriptionText
            //Получаем доступ к менеджеру нотификаций
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            //Регистрируем канал
            notificationManager.createNotificationChannel(mChannel)
        }

    }

    companion object {
        lateinit var instance: App
            private set
    }
}