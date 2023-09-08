package com.group4.ticketingservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
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
