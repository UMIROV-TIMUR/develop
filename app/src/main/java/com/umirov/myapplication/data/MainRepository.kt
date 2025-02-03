package com.umirov.myapplication.data

import androidx.lifecycle.LiveData
import com.umirov.myapplication.data.Entity.Film
import com.umirov.myapplication.data.dao.FilmDao
import java.util.concurrent.Executors

class MainRepository(private val filmDao: FilmDao) {

    fun putToDb(films: List<Film>) {
        //Запросы в бд должны быть в отдельном потоке
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films)

        }
    }

    fun getAllFromDB(): LiveData<List<Film>> = filmDao.getCachedFilms()
    }
