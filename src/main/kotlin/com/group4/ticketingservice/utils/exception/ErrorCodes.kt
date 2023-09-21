package com.group4.ticketingservice.utils.exception

import org.springframework.http.HttpStatus

enum class ErrorCodes(val status: HttpStatus, val message: String, val errorCode: Int) {
    // 400 - Bad Request
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "유효성 검증에 실패하였습니다.", -10001),

    // 401 - Unauthorized
    LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "로그인에 실패했습니다", -20000),

    // 403 - Forbidden
    NOT_OWNER_OF_RESERVATION(HttpStatus.FORBIDDEN, "예약의 주인이 아닙니다.", -30000),

    // 404 - Not Found
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 레코드를 찾을수 없습니다.", -40000),

    // 409 - Conflict
    EVENT_ALREADY_RESERVED_ALL(HttpStatus.CONFLICT, "이미 예약정원이 가득찬 공연입니다.", -50001),
    DUPLICATED_EMAIL_ADDRESS(HttpStatus.CONFLICT, "이미 가입된 이메일 주소입니다", -50002),

    // 500 - Internal Server Error

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "일시적인 장애입니다.", -10000)
}
