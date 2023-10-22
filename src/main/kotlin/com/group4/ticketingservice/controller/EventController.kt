package com.group4.ticketingservice.controller

import com.group4.ticketingservice.dto.EventCreateRequest
import com.group4.ticketingservice.dto.EventResponse
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.service.EventService
import com.group4.ticketingservice.utils.exception.CustomException
import com.group4.ticketingservice.utils.exception.ErrorCodes
import io.swagger.v3.oas.annotations.Hidden
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime

@RestController
@RequestMapping("/events")
class EventController @Autowired constructor(
    val eventService: EventService
) {
    companion object {
        const val DESCENDING = "desc"
        const val ASCENDING = "asc"
        const val SORT_BY_DEADLINE = "deadline"
        const val SORT_BY_START_DATE = "startDate"
        const val SORT_BY_CREATED_AT = "createdAt"
    }

    // TimeE2ETest를 위한 임시 EndPoint입니다.
    @Hidden
    @PostMapping
    fun createEvent(
        @RequestBody @Valid
        request: EventCreateRequest
    ): ResponseEntity<EventResponse> {
        val event = eventService.createEvent(
            request.name!!,
            request.startDate!!,
            request.endDate!!,
            request.reservationStartTime!!,
            request.reservationEndTime!!,
            request.maxAttendees!!
        )
        val response = EventResponse(
            id = event.id!!,
            name = event.name,
            startDate = event.startDate,
            endDate = event.endDate,
            reservationStartTime = event.reservationStartTime,
            reservationEndTime = event.reservationEndTime,
            maxAttendees = event.maxAttendees
        )

        val headers = HttpHeaders()
        headers.set("Content-Location", "/events/%d".format(event.id!!))

        return ResponseEntity(response, headers, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getEvent(request: HttpServletRequest, @PathVariable id: Int): ResponseEntity<EventResponse?> {
        val foundEvent = eventService.getEvent(id)?.let {
            EventResponse(
                id = it.id!!,
                name = it.name,
                startDate = it.startDate,
                endDate = it.endDate,
                reservationStartTime = it.reservationStartTime,
                reservationEndTime = it.reservationEndTime,
                maxAttendees = it.maxAttendees
            )
        } ?: kotlin.run {
            null
        }
        val headers = HttpHeaders()
        headers.set("Content-Location", request.requestURI)

        return ResponseEntity(foundEvent, headers, HttpStatus.OK)
    }

    @GetMapping
    fun getEvents(
        request: HttpServletRequest,
        @RequestParam sort: String,
        @RequestParam id: Int?,
        @RequestParam time: OffsetDateTime?
    ): ResponseEntity<Page<Event>> {
        val sortProperties = sort.split(",").toTypedArray()

        val fieldName = sortProperties.getOrNull(0)
        val direction = sortProperties.getOrNull(1)

        when (Pair(fieldName, direction)) {
            Pair(SORT_BY_DEADLINE, null),
            Pair(SORT_BY_START_DATE, null),
            Pair(SORT_BY_CREATED_AT, null),
            Pair(SORT_BY_DEADLINE, ASCENDING),
            Pair(SORT_BY_START_DATE, ASCENDING),
            Pair(SORT_BY_CREATED_AT, DESCENDING) -> {
                // Valid request
            }
            else -> throw CustomException(ErrorCodes.INVALID_SORT_FORMAT)
        }

        val page = eventService.getEvents(fieldName!!, id, time)

        val headers = HttpHeaders()
        headers.set("Content-Location", request.requestURI)

        return ResponseEntity(page, headers, HttpStatus.OK)
    }
}
