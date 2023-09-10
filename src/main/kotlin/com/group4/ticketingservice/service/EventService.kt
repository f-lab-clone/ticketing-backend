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
        name: String,
        eventStartTime: OffsetDateTime,
        eventEndTime: OffsetDateTime,
        maxAttendees: Int
    ): Event {
        val event = Event(
            name = name,
            eventStartTime = eventStartTime,
            eventEndTime = eventEndTime,
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
        name: String,
        eventStartTime: OffsetDateTime,
        eventEndTime: OffsetDateTime,
        maxAttendees: Int
    ): Event {
        val event = eventRepository.findById(id).orElseThrow {
            IllegalArgumentException("Event not found")
        }
        event.name = name
        event.eventStartTime = eventStartTime
        event.eventEndTime = eventEndTime
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
