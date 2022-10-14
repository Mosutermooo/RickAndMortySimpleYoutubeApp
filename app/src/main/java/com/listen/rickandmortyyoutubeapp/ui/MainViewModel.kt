package com.listen.rickandmortyyoutubeapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.listen.rickandmortyyoutubeapp.models.CharactersModel
import com.listen.rickandmortyyoutubeapp.network.RetrofitInstance
import com.listen.rickandmortyyoutubeapp.repositories.CharacterRepository
import com.listen.rickandmortyyoutubeapp.repositories.CharacterRepositoryImpl
import com.listen.rickandmortyyoutubeapp.uils.InternetConnection
import com.listen.rickandmortyyoutubeapp.uils.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

class MainViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val _state: MutableStateFlow<ScreenState<CharactersModel>> = MutableStateFlow(ScreenState.Idle())
    val state = _state.asStateFlow()

    private val connection = InternetConnection(app)

    private val repository: CharacterRepository = CharacterRepositoryImpl(RetrofitInstance.apiService)

    fun fetchCharacters() = viewModelScope.launch {
        _state.emit(ScreenState.Loading())
        try {
            if(connection.isAvailable){
                val response = repository.fetchCharacters()
                _state.emit(handleResponse(response))
            }else{
                _state.emit(ScreenState.Internet("Please turn on internet Connection"))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> {
                    _state.emit(ScreenState.Error("IOException"))
                }
                else -> _state.emit(ScreenState.Error(t.message.toString()))
            }
        }catch (s: SocketTimeoutException){
            _state.emit(ScreenState.Error("Socket-Error"))
        }catch (c: ConnectException){
            _state.emit(ScreenState.Error("Connect-Exception"))
        }
    }

    private fun handleResponse(response: Response<CharactersModel>): ScreenState<CharactersModel> {
        if(response.isSuccessful){
            response.body()?.let {
                return ScreenState.Success(it)
            }
        }
        return ScreenState.Error(response.message())
    }

}