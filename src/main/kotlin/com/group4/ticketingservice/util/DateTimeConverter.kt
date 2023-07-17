package com.group4.ticketingservice.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeConverter : JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    private val FORMATTER = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS"
        .let { DateTimeFormatter.ofPattern(it) }

    override fun serialize(src: LocalDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(FORMATTER.format(src))
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDateTime? {
        return FORMATTER.parse(json!!.asJsonPrimitive.asString) as LocalDateTime?
    }
}
