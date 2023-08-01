package com.group4.ticketingservice.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Performance(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var title: String,

    var date: LocalDateTime,

    @Column(name = "booking_start_time")
    var bookingStartTime: LocalDateTime,

    @Column(name = "booking_end_time")
    var bookingEndTime: LocalDateTime,

    @Column(name = "max_attendees")
    var maxAttendees: Int
)
