package com.group4.ticketingservice.controller

import com.group4.ticketingservice.dto.EventCreateRequest
import com.group4.ticketingservice.dto.EventResponse
import com.group4.ticketingservice.service.EventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/events")
class EventController @Autowired constructor(
    val eventService: EventService
) {

    @PostMapping
    fun createEvent(@RequestBody request: EventCreateRequest): ResponseEntity<EventResponse> {
        val event = eventService.createEvent(
            request.title,
            request.date,
            request.eventStartTime,
            request.eventEndTime,
            request.maxAttendees
        )
        val response = EventResponse(
            id = event.id!!,
            title = event.title,
            date = event.date,
            eventStartTime = event.eventStartTime,
            eventEndTime = event.eventEndTime,
            maxAttendees = event.maxAttendees
        )
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

    @GetMapping("/{id}")
    fun getEvent(@PathVariable id: Long): ResponseEntity<EventResponse?> {
        return eventService.getEvent(id)?.let {
            ResponseEntity.status(HttpStatus.OK).body(
                EventResponse(
                    id = it.id!!,
                    title = it.title,
                    date = it.date,
                    eventStartTime = it.eventStartTime,
                    eventEndTime = it.eventEndTime,
                    maxAttendees = it.maxAttendees
                )
            )
        } ?: kotlin.run {
            ResponseEntity.status(HttpStatus.OK).body(null)
        }
    }

    @GetMapping("/")
    fun getEvents(): ResponseEntity<List<EventResponse>> {
        val events = eventService.getEvents()
        val response: List<EventResponse> = events.map {
            EventResponse(
                id = it.id!!,
                title = it.title,
                date = it.date,
                eventStartTime = it.eventStartTime,
                eventEndTime = it.eventEndTime,
                maxAttendees = it.maxAttendees
            )
        }
        return if (events.isEmpty()) {
            ResponseEntity.status(HttpStatus.NO_CONTENT).body(response)
        } else {
            ResponseEntity.status(HttpStatus.OK).body(response)
        }
    }

    @PutMapping("/{id}")
    fun updateEvent(
        @PathVariable id: Long,
        @RequestBody request: EventCreateRequest
    ): ResponseEntity<EventResponse> {
        val event = eventService.updateEvent(
            id,
            request.title,
            request.date,
            request.eventStartTime,
            request.eventEndTime,
            request.maxAttendees
        )
        val response = EventResponse(
            id = event.id!!,
            title = event.title,
            date = event.date,
            eventStartTime = event.eventStartTime,
            eventEndTime = event.eventEndTime,
            maxAttendees = event.maxAttendees
        )
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

    @DeleteMapping("/{id}")
    fun deleteEvent(@PathVariable id: Long): ResponseEntity<Unit> {
        eventService.deleteEvent(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
