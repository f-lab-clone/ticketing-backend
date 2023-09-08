package com.group4.ticketingservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class SeatTagMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @ManyToOne
    @JoinColumn(name = "stage_seat_id")
    var stageSeat: StageSeat? = null

    @ManyToOne
    @JoinColumn(name = "seat_tag_id")
    var seatTag: SeatTag? = null

    @ManyToOne
    @JoinColumn(name = "stage_id")
    var stage: Stage? = null
}
