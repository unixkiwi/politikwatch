package de.unixkiwi.politikwatch.data.core.remote

import de.unixkiwi.politikwatch.data.core.model.DawumApiResponse
import retrofit2.http.GET

interface DawumApi {
    companion object {
        const val BASE_URL = "https://api.dawum.de"
    }

    @GET("/")
    suspend fun getSurveys(): DawumApiResponse
}