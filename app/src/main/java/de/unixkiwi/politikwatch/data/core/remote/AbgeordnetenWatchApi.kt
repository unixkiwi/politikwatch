package de.unixkiwi.politikwatch.data.core.remote

import de.unixkiwi.politikwatch.data.awpolls.model.AWApiPollModel
import de.unixkiwi.politikwatch.data.core.model.AWApiResponseWrapper
import de.unixkiwi.politikwatch.data.votes.model.AWApiVoteModel
import retrofit2.http.GET
import retrofit2.http.Query

interface AbgeordnetenWatchApi {
    companion object {
        const val BASE_URL = "https://www.abgeordnetenwatch.de/api/v2/"
    }

    @GET("polls")
    suspend fun getPolls(
        @Query("range_start") rangeStart: Int,
        @Query("range_end") rangeEnd: Int,
        @Query("sort_by") sortBy: String = "field_poll_date"
    ): AWApiResponseWrapper<List<AWApiPollModel>>

    @GET("votes")
    suspend fun getVotes(
        @Query("range_start") rangeStart: Int,
        @Query("range_end") rangeEnd: Int,
        @Query("poll") pollId: Int
    ): AWApiResponseWrapper<List<AWApiVoteModel>>
}