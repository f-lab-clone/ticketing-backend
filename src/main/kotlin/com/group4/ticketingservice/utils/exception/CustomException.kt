package com.group4.ticketingservice.utils.exception


class CustomException(
    val errorCode: ErrorCodes
) : RuntimeException()
