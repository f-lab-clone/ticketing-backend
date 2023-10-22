package com.group4.ticketingservice.service

import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.repository.EventRepository
import com.group4.ticketingservice.repository.EventRepositorySupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val eventRepositorySupport: EventRepositorySupport

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

    fun getEvents(sort: String, id: Int?, time: OffsetDateTime?): Page<Event> {
        return PageImpl(eventRepositorySupport.getEvent(sort, id, time))
    }
}
