package com.group4.ticketingservice.Event

import com.group4.ticketingservice.AbstractIntegrationTest
import com.group4.ticketingservice.Reservation.ReservationTest
import com.group4.ticketingservice.dto.EventSpecifications
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.EventRepository
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.utils.Authority
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import java.time.Duration.ofHours
import java.time.OffsetDateTime

class EventRepositoryTest @Autowired constructor(
    @Autowired val eventRepository: EventRepository,
    @Autowired val userRepository: UserRepository
) : AbstractIntegrationTest() {

    val sampleUser = User(
        name = ReservationTest.testFields.testName,
        email = ReservationTest.testFields.testUserName,
        password = BCryptPasswordEncoder().encode(ReservationTest.testFields.password),
        authority = Authority.USER
    )

    val sampleEvent = Event(
        title = "test title",
        date = OffsetDateTime.now(),
        reservationEndTime = OffsetDateTime.now() + ofHours(2),
        reservationStartTime = OffsetDateTime.now() + ofHours(1),
        maxAttendees = 10
    )

    val sampleEvents = mutableListOf(
        Event(
            title = "정섭이의 코딩쇼",
            date = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now(),
            reservationStartTime = OffsetDateTime.now(),
            maxAttendees = 10
        ),
        Event(
            title = "민준이의 전국군가잘함",
            date = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now(),
            reservationStartTime = OffsetDateTime.now(),
            maxAttendees = 10
        ),
        Event(
            title = "하영이의 신작도서 팬싸인회",
            date = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now(),
            reservationStartTime = OffsetDateTime.now(),
            maxAttendees = 10
        ),
        Event(
            title = "준하의 스파르타 코딩 동아리 설명회",
            date = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now(),
            reservationStartTime = OffsetDateTime.now(),
            maxAttendees = 10
        ),
        Event(
            title = "군대에서 코딩 직군으로 복무하기 설명회",
            date = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now(),
            reservationStartTime = OffsetDateTime.now(),
            maxAttendees = 10
        )
    )

    @BeforeEach
    fun addData() {
        userRepository.save(sampleUser)
        sampleEvents.forEach {
            eventRepository.save(it)
        }
    }

    @AfterEach
    fun removeData() {
        userRepository.deleteAll()
        eventRepository.deleteAll()
    }

    @Test
    fun `EventRepository_save should return savedEvent`() {
        // given

        // when
        val savedEvent = eventRepository.save(sampleEvent)

        // then
        assertThat(savedEvent).isEqualTo(sampleEvent)
    }

    @Test
    fun `EventRepository_findByIdOrNull should return event`() {
        // given
        val savedEvent = eventRepository.save(sampleEvent)

        // when
        val foundEvent: Event? = eventRepository.findByIdOrNull(
            savedEvent.id!!
        )

        // then
        assert(foundEvent?.id == sampleEvent.id)
    }

    @Test
    fun `EventRepository_findAll should return list of events`() {
        // given

        // when
        val events = eventRepository.findAll()

        // then
        assertInstanceOf(List::class.java, events)
        assert(events.size > 0)
    }

    @Test
    fun `EventRepository_findAll should return page of events with searching`() {
        // given
        val pageSize = 10
        val pageable: Pageable = PageRequest.of(0, pageSize)
        val title = "코딩"
        val specification = EventSpecifications.withTitle(title)
        val `totalElementsTitleLike코딩`: Long = 3

        // when
        val result = eventRepository.findAll(specification, pageable)

        // then
        assertThat(result.totalElements).isEqualTo(`totalElementsTitleLike코딩`)
    }

    @Test
    fun `EventRepository_findAll should return page of events with pagination`() {
        // given
        val pageSize = 2
        val pageable: Pageable = PageRequest.of(0, pageSize)

        // when
        val result = eventRepository.findAll(pageable)

        // then
        assertThat(result.totalElements).isEqualTo(sampleEvents.size.toLong())
        assertThat(result.pageable.pageSize).isEqualTo(pageSize)
        assertThat(result.numberOfElements).isEqualTo(pageSize)
        assertThat(result.content.size).isEqualTo(pageSize)
    }

    @Test
    fun `EventRepository_findAll should return page of events with sorting`() {
        // given
        val pageSize = 10
        val pageable: Pageable = PageRequest.of(0, pageSize, Sort.by("title").ascending())

        // when
        val result = eventRepository.findAll(pageable)

        // then
        assertThat(result.totalElements).isEqualTo(sampleEvents.size.toLong())
        assertThat(result.content[0].title).isEqualTo(sampleEvents[4].title)
        assertThat(result.content[1].title).isEqualTo(sampleEvents[1].title)
        assertThat(result.content[2].title).isEqualTo(sampleEvents[0].title)
        assertThat(result.content[3].title).isEqualTo(sampleEvents[3].title)
        assertThat(result.content[4].title).isEqualTo(sampleEvents[2].title)
    }

    @Test
    fun `EventRepository_delete should delete event`() {
        // given
        val savedEvent = eventRepository.save(sampleEvent)

        // when
        eventRepository.deleteById(savedEvent.id!!)
        val deletedEvent = eventRepository.findByIdOrNull(savedEvent.id!!)

        // then
        assertThat(deletedEvent).isEqualTo(null)
    }
}
