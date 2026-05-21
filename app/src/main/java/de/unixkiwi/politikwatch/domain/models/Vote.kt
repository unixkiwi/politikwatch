package de.unixkiwi.politikwatch.domain.models

data class Vote(
    val id: Int,
    val entityType: String,
    val label: String,
    val voteType: VoteType,
    val noShowReason: NoShowReason,
    val otherNoShowReason: String?,
    val fraction: VoteFraction
)

enum class VoteType {
    YES, NO, ABSTAIN, NO_SHOW
}

enum class NoShowReason {
    MATERNITY_PROTECTION, FELL_ILL, OTHER
}

data class VoteFraction(
    val id: Int,
    val entityType: String,
    val label: String
)