package de.unixkiwi.politikwatch.data.surveys.local

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {
    private val moshi = Moshi.Builder().build()
    private val mapType = Types.newParameterizedType(Map::class.java, String::class.java, Float::class.javaObjectType)
    private val mapAdapter = moshi.adapter<Map<String, Float>>(mapType)

    @TypeConverter
    fun fromStringMap(value: Map<String, Float>?): String {
        return value?.let { mapAdapter.toJson(it) } ?: ""
    }

    @TypeConverter
    fun toStringMap(value: String): Map<String, Float> {
        if (value.isEmpty()) return emptyMap()
        return try {
            mapAdapter.fromJson(value) ?: emptyMap()
        } catch (e: Exception) {
            emptyMap()
        }
    }
}

