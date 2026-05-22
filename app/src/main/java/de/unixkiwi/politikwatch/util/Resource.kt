package de.unixkiwi.politikwatch.util

sealed class Resource<T>(
    val data: T? = null,
    val errorTitle: String? = null,
    val errorDetails: String? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(errorTitle: String, errorDetails: String?, data: T? = null) :
        Resource<T>(data, errorTitle, errorDetails)

    class Loading<T>(val isLoading: Boolean = true) : Resource<T>()
}