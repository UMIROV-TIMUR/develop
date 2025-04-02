package com.umirov.myapplication.domain

import com.umirov.myapplication.data.API
import com.umirov.myapplication.data.MainRepository
import com.umirov.myapplication.data.TmdbApi
import com.umirov.myapplication.data.entity.Film
import com.umirov.myapplication.data.entity.TmdbResults
import com.umirov.myapplication.data.preferences.PreferenceProvider
import com.umirov.myapplication.utils.Converter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(
    private val repo: MainRepository,
    private val retrofitService: TmdbApi,
    private val preferences: PreferenceProvider
) {
    val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    val progressBarState = Channel<Boolean>(Channel.CONFLATED)


    fun getFilmsFromApi(page: Int) {

        scope.launch {
            progressBarState.send(true)
        }

        //Метод getDefaultCategoryFromPreferences() будет нам получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page)
            .enqueue(object : Callback<TmdbResults> {
                override fun onResponse(call: Call<TmdbResults>, response: Response<TmdbResults>) {
                    //При успехе мы вызываем метод передаем onSuccess и в этот коллбэк список фильмов
                    val list = Converter.convertApiListToDTOList(response.body()?.tmdbFilms)
                    //Кладем фильмы в бд
                    scope.launch {
                        repo.putToDb(list)
                        progressBarState.send(false)

                    }

                }

                override fun onFailure(call: Call<TmdbResults>, t: Throwable) {
                    //В случае провала вызываем другой метод коллбека
                    scope.launch {
                        progressBarState.send(false)
                    }
                }
            })
    }

    //Метод для сохранения настроек
    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }

    //Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preferences.geDefaultCategory()

    fun getFilmsFromDB(): Flow<List<Film>> = repo.getAllFromDB()
}

