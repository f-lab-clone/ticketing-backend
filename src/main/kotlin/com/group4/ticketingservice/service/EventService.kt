package com.group4.ticketingservice.service

import com.group4.ticketingservice.dto.EventResponse
import com.group4.ticketingservice.dto.EventSpecifications
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.repository.EventRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class EventService(
    private val eventRepository: EventRepository
) {
    fun createEvent(
        name: String,
        startDate: OffsetDateTime,
        endDate: OffsetDateTime,
        reservationStartTime: OffsetDateTime,
        reservationEndTime: OffsetDateTime,
        maxAttendees: Int
    ): Event {
        val event = Event(
            name = name,
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

    fun getEvents(name: String?, pageable: Pageable): Page<EventResponse> {
        val specification = EventSpecifications.withName(name)
        return PageImpl(
            eventRepository.findAllBy(specification, pageable).map {
                EventResponse(
                    id = it.id!!,
                    name = it.name,
                    startDate = it.startDate,
                    endDate = it.endDate,
                    reservationStartTime = it.reservationStartTime,
                    reservationEndTime = it.reservationEndTime,
                    maxAttendees = it.maxAttendees
                )
            }
        )
    }
}
