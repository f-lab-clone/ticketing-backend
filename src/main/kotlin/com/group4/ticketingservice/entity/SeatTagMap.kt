package com.group4.ticketingservice.entity

import jakarta.persistence.*

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