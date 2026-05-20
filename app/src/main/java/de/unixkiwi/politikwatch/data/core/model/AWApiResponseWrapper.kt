package de.unixkiwi.politikwatch.data.core.model

import kotlinx.serialization.Serializable

@Serializable
data class AWApiResponseWrapper<T>(
    val meta: AWApiResponseWrapperMeta,
    val data: T
)

data class AWApiResponseWrapperMeta(
    val status: String, // "ok", "warning" or "error"
    val status_message: String, // empty for "ok", warnings separated by pipe for "warning", error message for "error"
    val result: AWApiResponseWrapperResult,
)

data class AWApiResponseWrapperResult(
    val count: Int,
    val total: Int,
    val range_start: Int,
    val range_end: Int
)