package com.umirov.myapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.umirov.myapplication.data.Entity.Film
import com.umirov.myapplication.data.dao.FilmDao


@Database(entities = [Film::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}

