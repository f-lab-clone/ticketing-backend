package com.group4.ticketingservice.service

import com.group4.ticketingservice.dto.EventSpecifications
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.repository.EventRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class EventService(
    private val eventRepository: EventRepository
) {
    fun createEvent(
        title: String,
        startDate: OffsetDateTime,
        endDate: OffsetDateTime,
        reservationStartTime: OffsetDateTime,
        reservationEndTime: OffsetDateTime,
        maxAttendees: Int
    ): Event {
        val event = Event(
            title = title,
            startDate = startDate,
            endDate = endDate,
            reservationStartTime = reservationStartTime,
            reservationEndTime = reservationEndTime,
            maxAttendees = maxAttendees
        )
        return eventRepository.save(event)
    }

    fun getEvent(id: Int): Event? {
        return eventRepository.findById(id).orElse(null)
    }

    fun getEvents(title: String?, pageable: Pageable): Page<Event> {
        val specification = EventSpecifications.withTitle(title)
        return eventRepository.findAll(specification, pageable)
    }
}
