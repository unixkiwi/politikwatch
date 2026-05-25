package de.unixkiwi.politikwatch.domain.models

import de.unixkiwi.politikwatch.data.surveys.local.entity.InstituteEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.MethodEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.ParliamentEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.PartyEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.SurveyEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.TaskerEntity
import java.time.LocalDate

object SurveyMapper {

    fun toSurveyDomain(
        survey: SurveyEntity,
        parliament: ParliamentEntity?,
        institute: InstituteEntity?,
        tasker: TaskerEntity?,
        method: MethodEntity?,
        result: Map<DawumParty, Float>
    ): Survey {
        return Survey(
            id = survey.id,
            date = LocalDate.parse(survey.date),
            surveyPeriodStart = LocalDate.parse(survey.surveyPeriodStart),
            surveyPeriodEnd = LocalDate.parse(survey.surveyPeriodEnd),
            surveyedPersons = survey.surveyedPersons.toInt(),
            parliamentName = parliament?.name ?: "Unknown",
            parliamentShortcut = parliament?.shortcut ?: "N/A",
            instituteName = institute?.name ?: "Unknown",
            taskerName = tasker?.name ?: "Unknown",
            methodName = method?.name ?: "Unknown",
            results = result
        )
    }

    fun toDawumPartyDomain(partyEntity: PartyEntity): DawumParty {
        return DawumParty(
            name = partyEntity.name,
            shortcut = partyEntity.shortcut
        )
    }
}

