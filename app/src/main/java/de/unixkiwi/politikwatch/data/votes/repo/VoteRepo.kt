package de.unixkiwi.politikwatch.data.votes.repo

import de.unixkiwi.politikwatch.data.core.remote.AbgeordnetenWatchApi
import de.unixkiwi.politikwatch.domain.models.Vote
import javax.inject.Inject

class VoteRepository @Inject constructor(
    private val api: AbgeordnetenWatchApi
) {
    suspend fun getVotes(pollId: Int): List<Vote> {
        val batchSize = 500
        val allVotes = mutableListOf<Vote>()
        var rangeStart = 0
        var total = Int.MAX_VALUE

        while (allVotes.size < total) {
            val remaining = if (total == Int.MAX_VALUE) batchSize else total - allVotes.size
            val requestSize = minOf(batchSize, remaining)

            val response = api.getVotes(
                rangeStart = rangeStart,
                rangeEnd = rangeStart + requestSize,
                pollId = pollId
            )

            val votes = response.data.map { it.toDomain() }
            allVotes += votes

            total = response.meta.result.total
            val returnedCount = response.meta.result.count

            if (total == 0 || returnedCount == 0 || votes.isEmpty()) {
                break
            }

            rangeStart += returnedCount
        }

        return allVotes.take(total)
    }
}


