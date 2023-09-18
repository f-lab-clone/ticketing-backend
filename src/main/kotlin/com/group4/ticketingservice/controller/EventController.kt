package com.group4.ticketingservice.controller

import com.group4.ticketingservice.dto.EventResponse
import com.group4.ticketingservice.service.EventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/events")
class EventController @Autowired constructor(
    val eventService: EventService
) {

    @GetMapping("/{id}")
    fun getEvent(@PathVariable id: Int): ResponseEntity<EventResponse?> {
        return eventService.getEvent(id)?.let {
            ResponseEntity.status(HttpStatus.OK).body(
                EventResponse(
                    id = it.id!!,
                    title = it.title,
                    date = it.date,
                    reservationStartTime = it.reservationStartTime,
                    reservationEndTime = it.reservationEndTime,
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
                reservationStartTime = it.reservationStartTime,
                reservationEndTime = it.reservationEndTime,
                maxAttendees = it.maxAttendees
            )
        }
        return if (events.isEmpty()) {
            ResponseEntity.status(HttpStatus.NO_CONTENT).body(response)
        } else {
            ResponseEntity.status(HttpStatus.OK).body(response)
        }
    }
}
