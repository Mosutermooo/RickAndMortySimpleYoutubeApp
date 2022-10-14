package com.listen.rickandmortyyoutubeapp.network

import com.listen.rickandmortyyoutubeapp.uils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

object RetrofitInstance {

    private val logger = HttpLoggingInterceptor()

    private fun okHttp(): OkHttpClient{
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(logger)
        return builder.build()
    }


    val apiService: ApiService = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp())
        .build().create(ApiService::class.java)



}