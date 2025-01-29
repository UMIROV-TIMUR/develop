package com.umirov.myapplication.data

import com.umirov.myapplication.data.Entity.TmdbResults
import retrofit2.Call
import javax.inject.Inject

class MainRepositoryImpl@Inject constructor(private val api: TmdbApi) : MainRepository {
    override fun getPopularFilms(apiKey: String, language: String, page: Int): Call<TmdbResults>{
        return api.getFilms(apiKey, language, page)
    }
}

