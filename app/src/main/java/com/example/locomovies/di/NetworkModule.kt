package com.example.locomovies.di

import com.example.locomovies.BuildConfig
import com.example.locomovies.data.network.ApiKeyInterceptor
import com.example.locomovies.data.network.OmdbApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesBaseUrl(): String {
        val baseUrl = "https://www.omdbapi.com"
        return baseUrl
    }


    @Provides
    @Singleton
    fun provideApiKeyInterceptor(): ApiKeyInterceptor {
        val apiKey = BuildConfig.OMDB_API_KEY
        return ApiKeyInterceptor(apiKey)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesOmdbApiService(retrofit: Retrofit): OmdbApiService {
        return retrofit.create(OmdbApiService::class.java)
    }

}