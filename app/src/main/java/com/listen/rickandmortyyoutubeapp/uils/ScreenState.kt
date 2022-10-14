package com.listen.rickandmortyyoutubeapp.uils

sealed class ScreenState<T>(val data: T? = null, val message: String? = null) {

    class Loading<T>(): ScreenState<T>()
    class Success<T>(data: T?): ScreenState<T>(data = data)
    class Error<T>(message: String?): ScreenState<T>(message = message)
    class Internet<T>(message: String?): ScreenState<T>(message = message)
    class Idle<T>(): ScreenState<T>()

}
