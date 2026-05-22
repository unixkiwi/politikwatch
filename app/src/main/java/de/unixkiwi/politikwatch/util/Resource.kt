package de.unixkiwi.politikwatch.util

sealed interface Resource<T> {
    data class Success<T>(val data: T?) : Resource<T>
    data class Error<T>(val errorTitle: String, val errorDetails: String?) : Resource<T>

    class Loading<T> : Resource<T>
}