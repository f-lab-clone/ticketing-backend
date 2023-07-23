package com.group4.ticketingservice.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class DateTimeConverterTest {
    private val gson: Gson = GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, DateTimeConverter()).create()


    @Test
    fun serialize() {
        val sampleLocalDateTimeObj = LocalDateTime.of(2021, 1, 1, 1, 1, 1, 1)

        val sampleLocalDateTimeStr: String = gson.toJson(sampleLocalDateTimeObj)

        assertEquals(sampleLocalDateTimeStr, "\"2021-01-01T01:01:01.0000000\"")
    }

    @Test
    fun deserialize() {
        val sampleLocalDateTimeStr = "\"2021-01-01T01:01:01.0000000\""

        val sampleLocalDateTimeObj: LocalDateTime = gson.fromJson(sampleLocalDateTimeStr, LocalDateTime::class.java)

        assertEquals(sampleLocalDateTimeObj, LocalDateTime.of(2021, 1, 1, 1, 1, 1, 1))
    }
}