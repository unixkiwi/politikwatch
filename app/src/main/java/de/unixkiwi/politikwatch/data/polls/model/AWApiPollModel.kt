package de.unixkiwi.politikwatch.data.polls.model

import de.unixkiwi.politikwatch.domain.models.Poll
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class AWApiPollModel(
    val id: Int,
    val entity_type: String,
    val label: String,
    val field_intro: String,
    val field_accepted: Boolean,
    val field_poll_date: String
) {
    fun toDomain(): Poll {
        return Poll(
            id = id,
            entityType = entity_type,
            label = label,
            description = field_intro,
            accepted = field_accepted,
            date = LocalDate.parse(field_poll_date)
        )
    }
}