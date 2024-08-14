package com.example.locomovies.di

import com.example.locomovies.BuildConfig
import com.example.locomovies.data.network.OmdbApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://www.omdbapi.com/?apikey=${BuildConfig.OMDB_API_KEY}"

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesOmdbApiService(retrofit: Retrofit): OmdbApiService {
        return retrofit.create(OmdbApiService::class.java)
    }

}