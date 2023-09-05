package com.group4.ticketingservice.entity

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var name: String,

    @ManyToOne
    @JoinColumn(name = "hall_id")
    var hall: Hall? = null,

    @Column(name = "event_start_time")
    var eventStartTime: OffsetDateTime,

    @Column(name = "event_end_time")
    var eventEndTime: OffsetDateTime,

    @Column(name = "max_attendees")
    var maxAttendees: Int
)
