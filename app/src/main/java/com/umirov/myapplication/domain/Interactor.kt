package com.umirov.myapplication.domain

import com.umirov.myapplication.data.API
import com.umirov.myapplication.data.MainRepository
import com.umirov.myapplication.data.entity.Film
import com.umirov.myapplication.data.preferences.PreferenceProvider
import com.umirov.myapplication.utils.Converter
import com.umirov.remote_module.TmdbApi
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject


class Interactor(
    private val repo: MainRepository,
    private val retrofitService: TmdbApi,
    private val preferences: PreferenceProvider
) {
    var progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()


    fun getFilmsFromApi(page: Int) {
        //Показываем ProgressBar
        progressBarState.onNext(true)
        //Метод getDefaultCategoryFromPreferences() будет нам получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(
            apiKey = API.KEY,
            language = "ru-RU",
            query = getDefaultCategoryFromPreferences(),
            page = page
        )

            .subscribeOn(Schedulers.io())
            .map {
                Converter.convertApiListToDTOList(it.tmdbFilms)
            }
            .subscribeBy(
                onError = {
                    progressBarState.onNext(false)

                },
                onNext = {
                    progressBarState.onNext(false)
                    repo.putToDb(it)


                }
            )
    }

    fun getSearchResultFromApi(search: String): Observable<List<Film>> =
        retrofitService.getFilmFromSearch(API.KEY, "ru-RU", search, 1)
            .map {
                Converter.convertApiListToDTOList(it.tmdbFilms)
            }

    //Метод для сохранения настроек
    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }

    //Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preferences.geDefaultCategory()

    fun getFilmsFromDB(): Observable<List<Film>> = repo.getAllFromDB()
}

