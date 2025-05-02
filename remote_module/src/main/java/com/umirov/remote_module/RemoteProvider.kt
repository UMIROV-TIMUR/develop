package com.umirov.remote_module

interface RemoteProvider {
    fun provideRemote() : TmdbApi
}