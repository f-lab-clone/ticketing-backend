package com.group4.ticketingservice.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class DateTimeConverter : JsonSerializer<OffsetDateTime>, JsonDeserializer<OffsetDateTime> {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")

    override fun serialize(src: OffsetDateTime, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(
            (if (src.offset != ZoneOffset.UTC) src.withOffsetSameInstant(ZoneOffset.UTC) else src).format(formatter)
        )
    }

    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): OffsetDateTime {
        val offsetDatetime = OffsetDateTime.parse(json.asJsonPrimitive.asString, formatter)
        return if (offsetDatetime.offset != ZoneOffset.UTC) offsetDatetime.withOffsetSameInstant(ZoneOffset.UTC) else offsetDatetime
    }
}
