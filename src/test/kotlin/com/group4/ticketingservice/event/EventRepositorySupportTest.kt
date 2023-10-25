package com.group4.ticketingservice.event
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.entity.QEvent.event
import com.group4.ticketingservice.repository.EventRepositorySupport
import com.group4.ticketingservice.utils.exception.CustomException
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.OffsetDateTime

class EventRepositorySupportTest {
    private val queryFactory: JPAQueryFactory = mockk()
    private val query: JPAQuery<Event> = mockk()
    private val eventRepositorySupport = EventRepositorySupport(queryFactory)
    val content = mutableListOf(
        Event(
            id = 2,
            name = "민준이의 전국군가잘함",
            startDate = OffsetDateTime.now(),
            endDate = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now() + Duration.ofHours(2),
            reservationStartTime = OffsetDateTime.now() - Duration.ofHours(2),
            maxAttendees = 10
        ),
        Event(
            id = 1,
            name = "정섭이의 코딩쇼",
            startDate = OffsetDateTime.now(),
            endDate = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now() + Duration.ofHours(2),
            reservationStartTime = OffsetDateTime.now() - Duration.ofHours(2),
            maxAttendees = 10
        ),
        Event(
            id = 4,
            name = "준하의 스파르타 코딩 동아리 설명회",
            startDate = OffsetDateTime.now(),
            endDate = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now() + Duration.ofHours(2),
            reservationStartTime = OffsetDateTime.now() - Duration.ofHours(2),
            maxAttendees = 10
        ),
        Event(
            id = 3,
            name = "하영이의 신작도서 팬싸인회",
            startDate = OffsetDateTime.now(),
            endDate = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now() + Duration.ofHours(2),
            reservationStartTime = OffsetDateTime.now() - Duration.ofHours(2),
            maxAttendees = 10
        )
    )

    @Test
    fun `getEvent with valid sortBy deadline with last_access_Info  return Events `() {
        every { queryFactory.selectFrom(event).where(any()).orderBy(any(), any()).limit(any()).fetch() } returns content
        val sortBy = "deadline"
        val id = 1
        val dateTime = OffsetDateTime.now()

        val events = eventRepositorySupport.getEvent(sortBy, id, dateTime)

        assertNotNull(events)
        assertTrue(events.isNotEmpty())
    }

    @Test
    fun `getEvent with valid sortBy deadline  return Events `() {
        every { queryFactory.selectFrom(event).where(any()).orderBy(any(), any()).limit(any()).fetch() } returns content
        val sortBy = "deadline"

        val events = eventRepositorySupport.getEvent(sortBy, null, null)

        assertNotNull(events)
        assertTrue(events.isNotEmpty())
    }

    @Test
    fun `getEvent with valid sortBy startDate with last_access_Info  return Events `() {
        every { queryFactory.selectFrom(event).where(any()).orderBy(any(), any()).limit(any()).fetch() } returns content
        val sortBy = "startDate"
        val id = 1
        val dateTime = OffsetDateTime.now()

        val events = eventRepositorySupport.getEvent(sortBy, id, dateTime)

        assertNotNull(events)
        assertTrue(events.isNotEmpty())
    }

    @Test
    fun `getEvent with valid sortBy startDate return Events`() {
        every { queryFactory.selectFrom(event).where(any()).orderBy(any(), any()).limit(any()).fetch() } returns content
        val sortBy = "startDate"

        val events = eventRepositorySupport.getEvent(sortBy, null, null)

        assertNotNull(events)
        assertTrue(events.isNotEmpty())
    }

    @Test
    fun `getEvent with valid sortBy createdAt with last_access_Info  return Events `() {
        every { queryFactory.selectFrom(event).where(any()).orderBy(any(), any()).limit(any()).fetch() } returns content
        val sortBy = "createdAt"
        val id = 1
        val dateTime = OffsetDateTime.now()

        val events = eventRepositorySupport.getEvent(sortBy, id, dateTime)

        assertNotNull(events)
        assertTrue(events.isNotEmpty())
    }

    @Test
    fun `getEvent with valid sortBy createdAt return Events`() {
        every { queryFactory.selectFrom(event).where(any()).orderBy(any(), any()).limit(any()).fetch() } returns content
        val sortBy = "createdAt"
        val events = eventRepositorySupport.getEvent(sortBy, null, null)

        assertNotNull(events)
        assertTrue(events.isNotEmpty())
    }

    @Test
    fun `getEvent with invalid sortBy throws CustomException`() {
        val sortBy = "invalid"
        assertThrows(CustomException::class.java) {
            eventRepositorySupport.getEvent(sortBy, null, null)
        }
    }
}
