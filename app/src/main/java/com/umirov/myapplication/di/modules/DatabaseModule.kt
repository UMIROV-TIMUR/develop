package com.umirov.myapplication.di.modules

import android.content.Context
import androidx.room.Room
import com.umirov.myapplication.data.MainRepository
import com.umirov.myapplication.data.dao.FilmDao
import com.umirov.myapplication.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideFilmDao(context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "film_db"
    ).build().filmDao()


    @Provides
    @Singleton
    fun provideRepository(FilmDao: FilmDao) = MainRepository(FilmDao)

}