package com.ss.gpacalculator.utils

sealed class State<T>(val data: T? = null, val message: String? = null) {
    class Idle<T> : State<T>()
    class Progress<T> : State<T>()
    class Success<T>(data: T?): State<T>(data = data)
    class Error<T>(message: String): State<T>(message = message)
}