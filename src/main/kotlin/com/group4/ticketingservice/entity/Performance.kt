package com.group4.ticketingservice.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Performance(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val title: String,

    val date: LocalDateTime,

    @Column(name = "booking_start_time")
    val bookingStartTime: LocalDateTime,

    @Column(name = "booking_end_time")
    val bookingEndTime: LocalDateTime,

    @Column(name = "max_attendees")
    val maxAttendees: Int
)
