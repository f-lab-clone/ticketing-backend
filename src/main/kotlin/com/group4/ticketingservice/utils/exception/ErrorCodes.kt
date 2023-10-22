package com.group4.ticketingservice.utils.exception

import org.springframework.http.HttpStatus

enum class ErrorCodes(val status: HttpStatus, val message: String, val errorCode: Int) {
    // 400  Bad Request
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "유효성 검증에 실패하였습니다.", 10001),
    MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, "올바른 형식의 요청이 아닙니다", 10002),
    DATE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "예약 가능한 시간이 아닙니다.", 10003),
    INVALID_SORT_FORMAT(HttpStatus.BAD_REQUEST, "지원하는 정렬 형식이 아닙니다.", 10004),

    // 401  Unauthorized
    LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "로그인에 실패했습니다", 20000),
    JWT_AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "JWT 인증에 실패 하였습니다.", 20001),

    // 403  Forbidden
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 할 수 없습니다.", 30000),
    JWT_EXPIRED(HttpStatus.FORBIDDEN, "JWT가 만료 되었습니다.", 30001),

    // 404  Not Found
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 레코드를 찾을수 없습니다.", 40000),
    END_POINT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 엔드포인트를 찾을수업습니다.", 40400),
    TEST_ERROR(HttpStatus.NOT_FOUND, "테스트 입니다.", 40401),

    // 409  Conflict
    EVENT_ALREADY_RESERVED_ALL(HttpStatus.CONFLICT, "이미 예약정원이 가득찬 공연입니다.", 50001),
    DUPLICATED_EMAIL_ADDRESS(HttpStatus.CONFLICT, "이미 가입된 이메일 주소입니다", 50002),

    // 415 media type not support
    NOT_SUPPORTED_HTTP_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원 하지않는 미디어 타입 입니다.", 50003),
    // 500  Internal Server Error

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "일시적인 장애입니다.", 10000)
}
