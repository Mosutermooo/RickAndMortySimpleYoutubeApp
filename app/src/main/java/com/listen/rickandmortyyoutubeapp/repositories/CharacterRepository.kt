package com.listen.rickandmortyyoutubeapp.repositories

import com.listen.rickandmortyyoutubeapp.models.CharactersModel
import retrofit2.Response

interface CharacterRepository {
    suspend fun fetchCharacters(): Response<CharactersModel>
}