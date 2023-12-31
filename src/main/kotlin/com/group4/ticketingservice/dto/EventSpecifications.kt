package com.group4.ticketingservice.dto

import com.group4.ticketingservice.entity.Event
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification

class EventSpecifications {
    companion object {
        fun withName(name: String?): Specification<Event> {
            return Specification { root: Root<Event>, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder ->
                val predicates = mutableListOf<Predicate>()

                if (!name.isNullOrBlank()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%${name.toLowerCase()}%"))
                }

                criteriaBuilder.and(*predicates.toTypedArray())
            }
        }
    }
}
