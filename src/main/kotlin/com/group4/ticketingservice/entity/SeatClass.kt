package com.group4.ticketingservice.entity

import jakarta.persistence.*

@Entity
class SeatClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
    var name: String? = null
    var price: Int = 0
    @Column(name = "event_id")
    var eventId: Int? = null
}