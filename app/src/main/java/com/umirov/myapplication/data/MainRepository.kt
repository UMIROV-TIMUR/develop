package com.umirov.myapplication.data

import com.umirov.myapplication.data.Entity.TmdbResults
import retrofit2.Call

interface MainRepository {
    fun getPopularFilms(apiKey: String, language: String, page: Int): Call<TmdbResults>
}