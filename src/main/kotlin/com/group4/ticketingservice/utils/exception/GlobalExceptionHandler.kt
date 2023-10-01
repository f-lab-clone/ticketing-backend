package com.group4.ticketingservice.utils.exception

import com.group4.ticketingservice.dto.ErrorResponseDTO
import com.group4.ticketingservice.utils.logging.logger
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = logger()

    @ExceptionHandler(value = [CustomException::class])
    fun handleCustomException(exception: CustomException, request: HttpServletRequest): ResponseEntity<ErrorResponseDTO> {
        val errorCode = exception.errorCode
        val errorDto = ErrorResponseDTO(
            errorCode = exception.errorCode.errorCode,
            message = exception.errorCode.message,
            path = request.requestURI
        )
        return ResponseEntity(errorDto, errorCode.status)
    }

    @ExceptionHandler(value = [NoHandlerFoundException::class])
    fun handleEndpointNotFound(exception: NoHandlerFoundException, request: HttpServletRequest): ResponseEntity<ErrorResponseDTO> {
        val errorCode = ErrorCodes.END_POINT_NOT_FOUND
        val errorDto = ErrorResponseDTO(
            errorCode = errorCode.errorCode,
            message = exception.javaClass.name,
            path = request.requestURI
        )

        return ResponseEntity(errorDto, errorCode.status)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleServerError(exception: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponseDTO> {
        val errorCode = ErrorCodes.INTERNAL_SERVER_ERROR
        val errorDto = ErrorResponseDTO(
            errorCode = errorCode.errorCode,
            message = exception.javaClass.name,
            path = request.requestURI
        )
        log.warn(exception.message)
        return ResponseEntity(errorDto, errorCode.status)
    }

    @ExceptionHandler(value = [ExpiredJwtException::class])
    fun handleJwtExpiredError(exception: ExpiredJwtException, request: HttpServletRequest): ResponseEntity<ErrorResponseDTO> {
        val errorCode = ErrorCodes.JWT_EXPIRED
        val errorDto = ErrorResponseDTO(
            errorCode = errorCode.errorCode,
            message = exception.message!!,
            path = request.requestURI
        )
        return ResponseEntity(errorDto, errorCode.status)
    }

    @ExceptionHandler(value = [JwtException::class])
    fun handleJwtError(exception: JwtException, request: HttpServletRequest): ResponseEntity<ErrorResponseDTO> {
        val errorCode = ErrorCodes.JWT_AUTHENTICATION_FAILED
        val errorDto = ErrorResponseDTO(
            errorCode = errorCode.errorCode,
            message = exception.message!!,
            path = request.requestURI
        )
        return ResponseEntity(errorDto, errorCode.status)
    }

    @ExceptionHandler(value = [AuthenticationException::class])
    fun handleAuthenticationError(exception: AuthenticationException, request: HttpServletRequest): ResponseEntity<ErrorResponseDTO> {
        val errorCode = ErrorCodes.JWT_AUTHENTICATION_FAILED
        val errorDto = ErrorResponseDTO(
            errorCode = errorCode.errorCode,
            message = exception.message!!,
            path = request.requestURI
        )

        return ResponseEntity(errorDto, errorCode.status)
    }

    @ExceptionHandler(value = [HttpMediaTypeNotSupportedException::class])
    fun handleNotSupportedType(exception: HttpMediaTypeNotSupportedException, request: HttpServletRequest): ResponseEntity<ErrorResponseDTO> {
        val errorCode = ErrorCodes.NOT_SUPPORTED_HTTP_MEDIA_TYPE
        val errorDto = ErrorResponseDTO(
            errorCode = errorCode.errorCode,
            message = exception.javaClass.name,
            path = request.requestURI
        )
        return ResponseEntity(errorDto, errorCode.status)
    }

    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    fun handleMessageNotReadable(exception: HttpMessageNotReadableException, request: HttpServletRequest): ResponseEntity<ErrorResponseDTO> {
        val errorCode = ErrorCodes.MESSAGE_NOT_READABLE
        val errorDto = ErrorResponseDTO(
            errorCode = errorCode.errorCode,
            message = exception.javaClass.name,
            path = request.requestURI
        )
        return ResponseEntity(errorDto, errorCode.status)
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handlingValidException(exception: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<ErrorResponseDTO> {
        val builder = StringBuilder()
        val array = mutableListOf<String>()

        for (fieldError in exception.fieldErrors) {
            builder.append("[")
            builder.append(fieldError.field)
            builder.append("](은)는 ")
            builder.append(fieldError.defaultMessage)
            builder.append(".(입력된 값: [")
            builder.append(fieldError.rejectedValue)
            builder.append("])")
            array.add(builder.toString())
            builder.clear()
        }

        val errorDto = ErrorResponseDTO(
            errorCode = ErrorCodes.VALIDATION_FAILED.errorCode,
            message = array.joinToString("  ||  "),
            path = request.requestURI
        )
        return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
    }
}
