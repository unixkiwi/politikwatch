package de.unixkiwi.politikwatch.data.polls.remote

import de.unixkiwi.politikwatch.data.core.model.AWApiResponseWrapper
import de.unixkiwi.politikwatch.data.polls.model.AWApiPollModel
import retrofit2.http.GET
import retrofit2.http.Query

interface AbgeordnetenWatchApi {
    companion object {
        const val BASE_URL = "https://www.abgeordnetenwatch.de/api/v2/"
    }

    @GET("polls")
    suspend fun getPolls(
        @Query("range_start") rangeStart: Int,
        @Query("range_end") rangeEnd: Int
    ): AWApiResponseWrapper<List<AWApiPollModel>>
}