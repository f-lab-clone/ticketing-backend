package com.group4.ticketingservice.entity

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var title: String,

    var date: OffsetDateTime,

    @Column(name = "reservation_start_time")
    var reservationStartTime: OffsetDateTime,

    @Column(name = "reservation_end_time")
    var reservationEndTime: OffsetDateTime,

    @Column(name = "max_attendees")
    var maxAttendees: Int,

){
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    val reservations : List<Reservation>?=null
}
