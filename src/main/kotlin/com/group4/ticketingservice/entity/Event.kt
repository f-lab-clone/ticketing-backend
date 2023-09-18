package com.group4.ticketingservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.OffsetDateTime

@Entity
class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @NotNull
    var title: String,

    @NotNull
    var date: OffsetDateTime,

    @NotNull
    var reservationStartTime: OffsetDateTime,

    @NotNull
    var reservationEndTime: OffsetDateTime,

    @NotNull
    var maxAttendees: Int,

    @NotNull
    var currentReservationCount: Int = 0

) {

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val bookmarks: List<Bookmark> = mutableListOf()

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val reservations: List<Reservation> = mutableListOf()
}
