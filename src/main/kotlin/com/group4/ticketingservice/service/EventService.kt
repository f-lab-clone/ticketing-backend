package com.group4.ticketingservice.service

import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.repository.EventRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class EventService(
    private val eventRepository: EventRepository
) {
    fun createEvent(
        title: String,
        date: OffsetDateTime,
        reservationStartTime: OffsetDateTime,
        reservationEndTime: OffsetDateTime,
        maxAttendees: Int
    ): Event {
        val event = Event(
            title = title,
            date = date,
            reservationStartTime = reservationStartTime,
            reservationEndTime = reservationEndTime,
            maxAttendees = maxAttendees
        )
        return eventRepository.save(event)
    }

    fun getEvent(id: Int): Event? {
        return eventRepository.findById(id).orElse(null)
    }

    fun getEvents(): List<Event> {
        return eventRepository.findAll()
    }
}
