package com.group4.ticketingservice.repository

import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.entity.QEvent.event
import com.group4.ticketingservice.utils.exception.CustomException
import com.group4.ticketingservice.utils.exception.ErrorCodes
import com.querydsl.core.types.OrderSpecifier
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

    fun getEvent(sortBy: String, id: Int?, dateTime: OffsetDateTime?): List<Event> {
        val whereSpecifier = buildWhereSpecifier(sortBy, id, dateTime)
        val orderSpecifier = buildOrderSpecifier(sortBy)

        return queryFactory.selectFrom(event)
            .where(whereSpecifier)
            .orderBy(orderSpecifier, event.id.desc())
            .limit(10)
            .fetch()
    }

    private fun buildWhereSpecifier(sortBy: String?, id: Int?, dateTime: OffsetDateTime?): BooleanExpression? {
        return when (sortBy) {
            "deadline" -> buildDeadlineSpecifier(id, dateTime)
            "startDate" -> buildStartDateSpecifier(id, dateTime)
            "createdAt" -> buildCreatedAtSpecifier(id, dateTime)
            else -> throw CustomException(ErrorCodes.MESSAGE_NOT_READABLE)
        }
    }

    private fun buildOrderSpecifier(sortBy: String?): OrderSpecifier<*> {
        return when (sortBy) {
            "deadline" -> event.reservationEndTime.asc()
            "startDate" -> event.startDate.asc()
            "createdAt" -> event.createdAt.desc()
            else -> event.id.desc()
        }
    }

    private fun buildDeadlineSpecifier(id: Int?, dateTime: OffsetDateTime?): BooleanExpression? {
        return if (id == null) {
            event.reservationEndTime.after(OffsetDateTime.now())
        } else {
            event.reservationEndTime.gt(dateTime).or(event.reservationEndTime.eq(dateTime).and(ltEventId(id)))
        }
    }

    private fun buildStartDateSpecifier(id: Int?, dateTime: OffsetDateTime?): BooleanExpression? {
        return if (id == null) {
            event.startDate.before(OffsetDateTime.now() + Duration.ofDays(60))
        } else {
            event.startDate.gt(dateTime).or(event.startDate.eq(dateTime).and(ltEventId(id)))
        }
    }

    private fun buildCreatedAtSpecifier(id: Int?, dateTime: OffsetDateTime?): BooleanExpression? {
        return if (id == null) {
            null
        } else {
            event.createdAt.lt(dateTime).or(event.createdAt.eq(dateTime).and(ltEventId(id)))
        }
    }

    private fun ltEventId(id: Int?): BooleanExpression? {
        return if (id == null) null else event.id.lt(id)
    }
}
