package com.group4.ticketingservice.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.OffsetDateTime

@Entity
class Performance(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var title: String,

    var date: OffsetDateTime,

    @Column(name = "booking_start_time")
    var bookingStartTime: OffsetDateTime,

    @Column(name = "booking_end_time")
    var bookingEndTime: OffsetDateTime,

    @Column(name = "max_attendees")
    var maxAttendees: Int
)
