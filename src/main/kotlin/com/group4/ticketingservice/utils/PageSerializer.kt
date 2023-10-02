package com.matchilling.api.rest.data

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.boot.jackson.JsonComponent
import org.springframework.data.domain.PageImpl
import java.io.IOException

@JsonComponent
class PageSerializer : JsonSerializer<PageImpl<*>>() {

    @Throws(IOException::class)
    override fun serialize(
        page: PageImpl<*>,
        jsonGenerator: JsonGenerator,
        serializerProvider: SerializerProvider
    ) {
        jsonGenerator.writeObject(page.content)
    }
}
