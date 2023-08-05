package com.group4.ticketingservice.service

import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.repository.EventRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class EventService(
    val eventRepository: EventRepository
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

    fun getEvent(id: Long): Event? {
        return eventRepository.findById(id).orElse(null)
    }

    fun getEvents(): List<Event> {
        return eventRepository.findAll()
    }

    fun updateEvent(
        id: Long,
        title: String,
        date: OffsetDateTime,
        reservationStartTime: OffsetDateTime,
        reservationEndTime: OffsetDateTime,
        maxAttendees: Int
    ): Event {
        val event = eventRepository.findById(id).orElseThrow {
            IllegalArgumentException("Event not found")
        }
        event.title = title
        event.date = date
        event.reservationStartTime = reservationStartTime
        event.reservationEndTime = reservationEndTime
        event.maxAttendees = maxAttendees

        return eventRepository.save(event)
    }

    fun deleteEvent(id: Long) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id)
        } else {
            throw IllegalArgumentException("Event not found")
        }
    }
}
