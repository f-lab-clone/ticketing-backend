package com.group4.ticketingservice.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.OffsetDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {

    @Column(updatable = false)
    lateinit var createdAt: OffsetDateTime

    @Column(updatable = true)
    lateinit var updatedAt: OffsetDateTime

    @PrePersist
    fun setBothColumn() {
        createdAt = OffsetDateTime.now()
        updatedAt = OffsetDateTime.now()
    }

    @PreUpdate
    fun setUpdatedColumn() {
        updatedAt = OffsetDateTime.now()
    }
}
