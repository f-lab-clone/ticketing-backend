package com.group4.ticketingservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class StageSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
    var row: Int? = null
    var col: Int? = null
    var limit: Int = 0

    @ManyToOne
    @JoinColumn(name = "stage_id")
    var stage: Stage? = null
}
