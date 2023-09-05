package com.group4.ticketingservice.entity

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
class EventTimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @ManyToOne
    @JoinColumn(name = "event_id")
    var event: Event? = null

    @ManyToOne
    @JoinColumn(name = "stage_id")
    var stage: Stage? = null

    var startDatetime: OffsetDateTime? = null
    var endDatetime: OffsetDateTime? = null
}