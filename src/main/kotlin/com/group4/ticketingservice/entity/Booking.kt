package com.group4.ticketingservice.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Booking(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @ManyToOne
    @JoinColumn(name = "performance_id", nullable = false)
    var performance: Performance,

    @Column(nullable = false)
    var bookedAt: LocalDateTime = LocalDateTime.now()
)
