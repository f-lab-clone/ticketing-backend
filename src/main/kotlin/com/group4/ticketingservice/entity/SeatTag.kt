package com.group4.ticketingservice.entity

import jakarta.persistence.*

@Entity
class SeatTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @ManyToOne
    @JoinColumn(name = "hall_id")
    var hall: Hall? = null

    var name: String? = null
}