package com.group4.ticketingservice.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Version
import java.time.OffsetDateTime

@Entity
class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var name: String,

    @OneToOne(fetch = FetchType.LAZY)
    var hall: Hall? = null,

    @Column(name = "event_start_time")
    var eventStartTime: OffsetDateTime,

    @Column(name = "event_end_time")
    var eventEndTime: OffsetDateTime,

    @Column(name = "max_attendees")
    var maxAttendees: Int,

    @Column(name = "available_attendees")
    var availableAttendees: Int = maxAttendees
) {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", targetEntity = Reservation::class)
    var reservations: List<Reservation>? = null

    @Version
    private val version: Long? = null
}
