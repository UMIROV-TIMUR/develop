package com.umirov.myapplication.di.modules

import com.umirov.myapplication.data.*
import com.umirov.myapplication.data.MainRepository
import com.umirov.myapplication.domain.Interactor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {


    @Binds
    @Singleton
    abstract fun bindMainRepository(impl: MainRepositoryImpl): MainRepository



    companion object{
        @Provides
        @Singleton
        fun provideInteractor(
            repository: MainRepository,
            tmdbApi: TmdbApi
        ) : Interactor{
            return Interactor(repository, tmdbApi)
        }



    }
    }


