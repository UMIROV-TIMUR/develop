package com.umirov.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umirov.myapplication.App
import com.umirov.myapplication.data.Entity.Film
import com.umirov.myapplication.domain.Interactor
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()

    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        getFilms()
    }

    fun getFilms() {
        interactor.getFilmsFromApi(1, object : ApiCallback {
            override fun onSuccess(films: List<Film>) {
                filmsListLiveData.postValue(films)
            }

            override fun onFailure() {
                filmsListLiveData.postValue(interactor.getFilmsFromDB())
            }
        })
    }
    // пагинация*
    fun getFilmsFromApi(page: Int, callback: ApiCallback) {
        interactor.getFilmsFromApi(page, object : ApiCallback {
            override fun onSuccess(films: List<Film>) {
                callback.onSuccess(films)

            }
            override fun onFailure() {
                callback.onFailure()
            }
        })

    }


    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }
}