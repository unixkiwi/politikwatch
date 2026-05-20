package de.unixkiwi.politikwatch.data.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AWApiResponseWrapper<T>(
    val status: String, // "ok", "warning" or "error"
    @SerialName("status_message") val statusMessage: String, // empty for "ok", warnings separated by pipe for "warning", error message for "error"
    val result: AWApiResponseWrapperResult,
    val data: T
)

data class AWApiResponseWrapperResult(
    val count: Int,
    val total: Int,
    val page: Int,
    @SerialName("results_per_page") val resultsPerPage: Int
)