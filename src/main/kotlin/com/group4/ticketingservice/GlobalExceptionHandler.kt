package com.group4.ticketingservice

import com.group4.ticketingservice.dto.ValidErrorDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handlingValidException(exception: MethodArgumentNotValidException): ResponseEntity<ValidErrorDto> {
        val builder = StringBuilder()
        val array = mutableListOf<String>()

        for (fieldError in exception.fieldErrors) {
            builder.append("[")
            builder.append(fieldError.field)
            builder.append("](은)는 ")
            builder.append(fieldError.defaultMessage)
            builder.append("  ||  입력된 값: [")
            builder.append(fieldError.rejectedValue)
            builder.append("]")
            array.add(builder.toString())
            builder.clear()
        }
        val errorDto = ValidErrorDto(errorCode = "${ HttpStatus.BAD_REQUEST}", errors = array)

        return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
    }
}
