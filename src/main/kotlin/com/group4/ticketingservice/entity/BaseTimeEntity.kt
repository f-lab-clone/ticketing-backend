package com.group4.ticketingservice.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.OffsetDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {

    @Column(updatable = false)
    val createdAt: OffsetDateTime = OffsetDateTime.now()

    @Column(updatable = true)
    val updatedAt: OffsetDateTime = OffsetDateTime.now()
}
