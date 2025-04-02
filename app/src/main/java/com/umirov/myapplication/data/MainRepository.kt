package com.umirov.myapplication.data

import com.umirov.myapplication.data.dao.FilmDao
import com.umirov.myapplication.data.entity.Film
import kotlinx.coroutines.flow.Flow

class MainRepository(private val filmDao: FilmDao) {
    fun putToDb(films: List<Film>) {
        filmDao.insertAll(films)
    }

    fun getAllFromDB(): Flow<List<Film>> = filmDao.getCachedFilms()
}

