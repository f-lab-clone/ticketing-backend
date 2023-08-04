package com.group4.ticketingservice

import com.group4.ticketingservice.dto.ErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {


    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handlingValidException(exception: MethodArgumentNotValidException) :ResponseEntity<ErrorResponseDto>{
        val errorDto = ErrorResponseDto(errorCode = "${ HttpStatus.BAD_REQUEST}", message = exception.bindingResult.allErrors[0].defaultMessage+" 형식에 맞게 다시 시도하십시오.")

        return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
    }
}