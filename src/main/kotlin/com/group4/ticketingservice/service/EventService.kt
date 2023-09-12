package com.group4.ticketingservice.service

import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.repository.EventRepository
import org.springframework.stereotype.Service

@Service
class EventService(
    private val eventRepository: EventRepository
) {

    fun getEvent(id: Long): Event? {
        return eventRepository.findById(id).orElse(null)
    }

    fun getEvents(): List<Event> {
        return eventRepository.findAll()
    }
}
