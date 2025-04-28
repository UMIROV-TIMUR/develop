package com.umirov.remote_module

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("3/search/movie")
    fun getFilms(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Observable<com.umirov.remote_module.entity.TmdbResults>

    @GET("3/search/movie")
    fun getFilmFromSearch(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Observable<com.umirov.remote_module.entity.TmdbResults>
}