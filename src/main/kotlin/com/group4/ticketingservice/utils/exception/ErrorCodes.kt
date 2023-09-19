package com.group4.ticketingservice.utils.exception

import org.springframework.http.HttpStatus

enum class ErrorCodes(val status: HttpStatus, val message: String) {
    // 400 - Bad Request
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "유효성 검증에 실패하였습니다."),

    // 401 - Unauthorized
    LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "로그인에 실패했습니다"),

    // 403 - Forbidden
    NOT_OWNER_OF_RESERVATION(HttpStatus.FORBIDDEN, "예약의 주인이 아닙니다."),


    // 404 - Not Found
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저 정보를 찾을 수 없습니다"),
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "이벤트 정보를 찾을 수 없습니다"),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약 정보를 찾을 수 없습니다"),


    // 409 - Conflict
    EVENT_ALREADY_RESERVED_ALL(HttpStatus.CONFLICT, "이미 예약정원이 가득찬 공연입니다."),
    DUPLICATED_EMAIL_ADDRESS(HttpStatus.CONFLICT, "이미 가입된 이메일 주소입니다"),


    // 500 - Internal Server Error

}
