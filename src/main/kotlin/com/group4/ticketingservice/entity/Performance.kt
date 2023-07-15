package com.group4.ticketingservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Column
import java.time.LocalDateTime

@Entity
class Performance(
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
