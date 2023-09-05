package com.group4.ticketingservice.entity

import jakarta.persistence.*

@Entity
class AvailableSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @ManyToOne
    @JoinColumn(name = "seat_class_id")
    var seatClass: SeatClass? = null

    @ManyToOne
    @JoinColumn(name = "stage_seat_id")
    var stageSeat: StageSeat? = null

    @ManyToOne
    @JoinColumn(name = "stage_id")
    var stage: Stage? = null
}
