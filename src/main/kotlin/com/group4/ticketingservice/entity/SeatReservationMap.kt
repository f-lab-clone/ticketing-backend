package com.group4.ticketingservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class SeatReservationMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @ManyToOne
    @JoinColumn(name = "available_seat_id")
    var availableSeat: AvailableSeat? = null

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    var reservation: Reservation? = null

    @ManyToOne
    @JoinColumn(name = "stage_id")
    var stage: Stage? = null
}
