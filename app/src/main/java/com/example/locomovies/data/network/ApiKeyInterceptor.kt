package com.example.locomovies.data.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    companion object {
        const val QUERY_PARAM_API_KEY = "apikey"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        // Add the API key as a query parameter to the original URL
        val urlWithApiKey = originalUrl.newBuilder()
            .addQueryParameter(QUERY_PARAM_API_KEY, apiKey)
            .build()

        // Build a new request with the modified URL
        val newRequest = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        return chain.proceed(newRequest)
    }

}