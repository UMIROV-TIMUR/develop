package com.umirov.myapplication.data

import com.umirov.myapplication.data.dao.FilmDao
import com.umirov.myapplication.data.entity.Film
import io.reactivex.rxjava3.core.Observable

class MainRepository(private val filmDao: FilmDao) {
    fun putToDb(films: List<Film>) {
        filmDao.insertAll(films)
    }

    fun getAllFromDB(): Observable<List<Film>> = filmDao.getCachedFilms()
}

