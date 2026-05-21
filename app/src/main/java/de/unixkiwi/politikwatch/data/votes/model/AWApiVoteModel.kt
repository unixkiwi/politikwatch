package de.unixkiwi.politikwatch.data.votes.model

import de.unixkiwi.politikwatch.domain.models.NoShowReason
import de.unixkiwi.politikwatch.domain.models.Vote
import de.unixkiwi.politikwatch.domain.models.VoteFraction
import de.unixkiwi.politikwatch.domain.models.VoteType
import kotlinx.serialization.Serializable

@Serializable
data class AWApiVoteModel(
    val id: Int,
    val entity_type: String,
    val label: String,
    val api_url: String,
    val vote: String, // "yes", "no", "abstain", "no_show"
    val reason_no_show: String?,
    val reason_no_show_other: String?,
    val fraction: AWApiVoteFractionModel
) {
    fun toDomain(): Vote {
        return Vote(
            id = id,
            entityType = entity_type,
            label = label,
            voteType = when (vote.lowercase()) {
                "yes" -> VoteType.YES
                "no" -> VoteType.NO
                "abstain" -> VoteType.ABSTAIN
                "no_show" -> VoteType.NO_SHOW
                else -> VoteType.NO_SHOW
            },
            noShowReason = when (reason_no_show?.lowercase()) {
                "maternity_protection" -> NoShowReason.MATERNITY_PROTECTION
                "fell_ill" -> NoShowReason.FELL_ILL
                "other" -> NoShowReason.OTHER
                null -> NoShowReason.OTHER
                else -> NoShowReason.OTHER
            },
            otherNoShowReason = reason_no_show_other,
            fraction = VoteFraction(
                id = fraction.id,
                entityType = fraction.entity_type,
                label = fraction.label
            )
        )
    }
}

@Serializable
data class AWApiVoteFractionModel(
    val id: Int,
    val entity_type: String,
    val label: String,
    val api_url: String
)

