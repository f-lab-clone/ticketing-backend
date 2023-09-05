package com.group4.ticketingservice.entity

import jakarta.persistence.*

@Entity
class Stage(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    @ManyToOne
    @JoinColumn(name = "hall_id")
    val hall: Hall,
    val name: String
)