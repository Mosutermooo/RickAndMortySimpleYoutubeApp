package com.listen.rickandmortyyoutubeapp.repositories

import com.listen.rickandmortyyoutubeapp.models.CharactersModel
import com.listen.rickandmortyyoutubeapp.network.ApiService
import retrofit2.Response

class CharacterRepositoryImpl(
    private val apiService: ApiService
) : CharacterRepository{
    override suspend fun fetchCharacters(): Response<CharactersModel> {
        return apiService.fetchCharacters()
    }
}