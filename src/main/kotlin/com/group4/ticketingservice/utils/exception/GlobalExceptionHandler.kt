package com.group4.ticketingservice.utils.exception

import com.group4.ticketingservice.dto.ErrorResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [CustomException::class])
    fun handlingCustomException(exception: CustomException): ResponseEntity<ErrorResponseDTO> {
        val errorCode = exception.errorCode
        val errorDto = ErrorResponseDTO(errorCode = errorCode.name, message = errorCode.message, errors = listOf<Any>())
        return ResponseEntity(errorDto, errorCode.status)
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handlingValidException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponseDTO> {
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
        val errorDto = ErrorResponseDTO(errorCode = ErrorCodes.VALIDATION_FAILED.name, errors = array, message = ErrorCodes.VALIDATION_FAILED.message)

        return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
    }
}
