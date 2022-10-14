package com.listen.rickandmortyyoutubeapp.network

import com.listen.rickandmortyyoutubeapp.models.CharactersModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("character")
    suspend fun fetchCharacters(): Response<CharactersModel>

}