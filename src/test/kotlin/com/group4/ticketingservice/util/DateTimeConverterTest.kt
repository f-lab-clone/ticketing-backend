package com.group4.ticketingservice.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.group4.ticketingservice.dto.BookingResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import java.time.ZoneOffset

class DateTimeConverterTest {
    private val gson: Gson = GsonBuilder().registerTypeAdapter(OffsetDateTime::class.java, DateTimeConverter()).create()

    @Test
    fun `serialize should return stringified OffsetDateTime`() {
        val sampleOffsetDateTimeObj = OffsetDateTime.of(
            2021, 1, 1, 1, 1, 1, 0,
            ZoneOffset.of("+09:00")
        )

        val sampleOffsetDateTimeStr: String = gson.toJson(sampleOffsetDateTimeObj)

        assertEquals(sampleOffsetDateTimeStr, "\"2020-12-31T16:01:01.000Z\"")
    }

    @Test
    fun `deserialize should return OffsetDateTime object`() {
        val sampleOffsetDateTimeStr = "\"2021-01-01T01:01:01.000+09:00\""

        val sampleOffsetDateTimeObj: OffsetDateTime = gson.fromJson(sampleOffsetDateTimeStr, OffsetDateTime::class.java)

        assertEquals(
            sampleOffsetDateTimeObj, OffsetDateTime.of(
                2020, 12, 31, 16, 1, 1, 0,
                ZoneOffset.of("+00:00")
            )
        )
    }

    @Test
    fun `serialize bookingResponse with KST Datetime should return stringified OffsetDateTime`() {
        val sampleOffsetDateTimeObj = OffsetDateTime.of(
            2021, 1, 1, 1, 1, 1, 0,
            ZoneOffset.of("+09:00")
        )

        val sampleOffsetDateTimeStr: String = gson.toJson(
            BookingResponse(
                id = 1,
                userId = 1,
                performanceId = 1,
                bookedAt = sampleOffsetDateTimeObj
            )
        )

        assertEquals(
            sampleOffsetDateTimeStr,
            "{\"id\":1,\"performanceId\":1,\"userId\":1,\"bookedAt\":\"2020-12-31T16:01:01.000Z\"}"
        )
    }

    @Test
    fun `deserialize bookingResponse with KST Datetime should return OffsetDateTime object`() {
        val sampleOffsetDateTimeStr =
            "{\"id\":1,\"performanceId\":1,\"userId\":1,\"bookedAt\":\"2021-01-01T01:01:01.000+09:00\"}"

        val sampleOffsetDateTimeObj: BookingResponse =
            gson.fromJson(sampleOffsetDateTimeStr, BookingResponse::class.java)

        assertEquals(
            sampleOffsetDateTimeObj.bookedAt, OffsetDateTime.of(
                2020, 12, 31, 16, 1, 1, 0,
                ZoneOffset.of("+00:00")
            )
        )
    }

}
