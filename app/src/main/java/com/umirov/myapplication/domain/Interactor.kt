package com.umirov.myapplication.domain

import com.umirov.myapplication.data.API
import com.umirov.myapplication.data.Entity.TmdbResults
import com.umirov.myapplication.data.MainRepository
import com.umirov.myapplication.data.TmdbApi
import com.umirov.myapplication.utils.Converter
import com.umirov.myapplication.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(val repo: MainRepository, private val retrofitService: TmdbApi) {
    //В конструктор мы будем передавать коллбек из вью модели, чтобы реагировать на то, когда фильмы будут получены
    //и страницу, которую нужно загрузить (это для пагинации)
    fun getFilmsFromApi(page: Int, callback:
    HomeFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(API.KEY, "ru-RU", page) .enqueue(object : Callback<TmdbResults> {
            override fun onResponse(call: Call<TmdbResults>, response: Response<TmdbResults>) {
                //При успехе мы вызываем метод успешного ответа из коллбека и передаем ему список фильмов
                callback.onSuccess(Converter.convertApiListToDTOList(response.body()?.tmdbFilms))
            }
            override fun onFailure(call: Call<TmdbResults>, t: Throwable) {
                //В случае провала вызываем другой метод коллбека
                callback.onFailure()
            }


        })
    }
}

