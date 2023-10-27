package com.group4.ticketingservice.Event

import com.group4.ticketingservice.AbstractIntegrationTest
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.repository.EventRepository
import com.group4.ticketingservice.repository.EventRepositorySupport
import com.group4.ticketingservice.repository.UserRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.OffsetDateTime
import java.time.ZoneOffset

class EventRepositorySupportTest(
    @Autowired val eventRepositorySupport: EventRepositorySupport,
    @Autowired val eventRepository: EventRepository,
    @Autowired val userRepository: UserRepository
) : AbstractIntegrationTest() {
    val sampleEvents = mutableListOf<Event>()
    var isSetup = false

    @BeforeEach
    fun addData() {
        if (!isSetup) {
            eventRepository.deleteAll()
            for (i in 1..20) {
                val event = Event(
                    name = i.toString(),
                    startDate = OffsetDateTime.now().plusDays((1..20).random().toLong()),
                    endDate = OffsetDateTime.now(),
                    reservationEndTime = OffsetDateTime.of(2024, 6, 30, 0, 0, 0, 0, ZoneOffset.UTC).plusDays((1..20).random().toLong()),
                    reservationStartTime = OffsetDateTime.now(),
                    maxAttendees = 10
                )
                sampleEvents.add(event)
            }
            eventRepository.saveAll(sampleEvents)
        }
        isSetup = true
    }

    @Test
    fun `eventRepositorySupportTest_getEvents should return sorted events by deadline`() {
        // given
        val sortBy = "deadline"
        val sortedEvents = sampleEvents.sortedWith(
            compareBy(
                { it.reservationEndTime },
                { -it.id!! }
            )
        )

        // when
        val firstResponse = eventRepositorySupport.getEvent(sortBy, null, null)
        val responseWithLastAccess = eventRepositorySupport.getEvent(sortBy, firstResponse[9].id, firstResponse[9].reservationEndTime)

        // then
        for (i in 0..9) {
            Assertions.assertThat(firstResponse[i].id).isEqualTo(sortedEvents[i].id)
        }
        for (i in 0..9) {
            Assertions.assertThat(responseWithLastAccess[i].id).isEqualTo(sortedEvents[i + 10].id)
        }
    }

    @Test
    fun `eventRepositorySupportTest_getEvents should return sorted events by startDate`() {
        // given
        val sortBy = "startDate"
        val sortedEvents = sampleEvents.sortedWith(
            compareBy(
                { it.startDate },
                { -it.id!! }
            )
        )

        // when
        val firstResponse = eventRepositorySupport.getEvent(sortBy, null, null)
        val responseWithLastAccess = eventRepositorySupport.getEvent(sortBy, firstResponse[9].id, firstResponse[9].startDate)

        // then
        for (i in 0..9) {
            Assertions.assertThat(firstResponse[i].id).isEqualTo(sortedEvents[i].id)
        }
        for (i in 0..9) {
            Assertions.assertThat(responseWithLastAccess[i].id).isEqualTo(sortedEvents[i + 10].id)
        }
    }

    @Test
    fun `eventRepositorySupportTest_getEvents should return sorted events by createdAt`() {
        // given
        val sortBy = "createdAt"
        val sortedEvents = sampleEvents.toList().reversed()

        // when
        val firstResponse = eventRepositorySupport.getEvent(sortBy, null, null)
        val responseWithLastAccess = eventRepositorySupport.getEvent(sortBy, firstResponse[9].id, firstResponse[9].createdAt)

        // then
        for (i in 0..9) {
            Assertions.assertThat(firstResponse[i].id).isEqualTo(sortedEvents[i].id)
        }
        for (i in 0..9) {
            Assertions.assertThat(responseWithLastAccess[i].id).isEqualTo(sortedEvents[i + 10].id)
        }
    }
}
