package com.umirov.myapplication.di

import com.umirov.myapplication.di.modules.DatabaseModule
import com.umirov.myapplication.di.modules.DomainModule
import com.umirov.myapplication.di.modules.RemoteModule
import com.umirov.myapplication.viewmodel.HomeFragmentViewModel
import com.umirov.myapplication.viewmodel.SettingsFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    //Внедряем все модули, нужные для этого компонента
    modules = [
        RemoteModule::class,
        DatabaseModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    //метод для того, чтобы появилась внедрять зависимости в HomeFragmentViewModel
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
    //метод для того, чтобы появилась возможность внедрять зависимости в SettingsFragmentViewModel
    fun inject(settingsFragmentViewModel: SettingsFragmentViewModel)
}