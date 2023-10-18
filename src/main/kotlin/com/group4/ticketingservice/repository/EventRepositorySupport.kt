package com.group4.ticketingservice.repository

import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.entity.QEvent.event
import com.group4.ticketingservice.utils.exception.CustomException
import com.group4.ticketingservice.utils.exception.ErrorCodes
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.Duration
import java.time.OffsetDateTime

@Repository
class EventRepositorySupport(
    private val queryFactory: JPAQueryFactory
) : QuerydslRepositorySupport(
    Event::class.java
) {

    private fun ltEventId(id: Int?): BooleanExpression? {
        return if (id == null) null else event.id.lt(id)
    }

    fun getEvent(sort: String?, id: Int?, dateTime: OffsetDateTime?): List<Event> {
        val whereSpecifier = if (id == null) {
            when (sort) {
                "deadline" -> event.reservationEndTime.after(OffsetDateTime.now())
                "startDate" -> event.startDate.before(OffsetDateTime.now() + Duration.ofDays(60))
                "createdAt" -> null
                null -> null
                else -> throw CustomException(ErrorCodes.MESSAGE_NOT_READABLE)
            }
        } else {
            when (sort) {
                "deadline" -> event.reservationEndTime.gt(dateTime).or(event.reservationEndTime.eq(dateTime).and(ltEventId(id)))
                "startDate" -> event.startDate.gt(dateTime).or(event.startDate.eq(dateTime).and(ltEventId(id)))
                "createdAt" -> event.createdAt.lt(dateTime).or(event.createdAt.eq(dateTime).and(ltEventId(id)))
                null -> ltEventId(id)
                else -> throw CustomException(ErrorCodes.MESSAGE_NOT_READABLE)
            }
        }

        val orderSpecifier = when (sort) {
            "deadline" -> event.reservationEndTime.asc()
            "startDate" -> event.startDate.asc()
            "createdAt" -> event.createdAt.desc()
            null -> event.id.desc()
            else -> throw CustomException(ErrorCodes.MESSAGE_NOT_READABLE)
        }

        return queryFactory.selectFrom(event)
            .where(whereSpecifier)
            .orderBy(orderSpecifier, event.id.desc())
            .limit(10)
            .fetch()
    }
}
