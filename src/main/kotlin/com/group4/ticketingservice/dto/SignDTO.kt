package com.group4.ticketingservice.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size


/**
 * 클래스 생성자에서 매개변수들을 받아 필드에 포함시키는 경우에는
 * Validation관련 어노테이션들이 필드에 붙는것이아닌
 * 생성자에 붙으므로 필드에 어노테이션을 적용해주겠다는 @field를 붙혀야함
 * ex) @NotNull -> @field:NotNull
 */

data class SignUpRequest(
        @field:NotNull
        @field:Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        val email: String?,
        @field:NotNull
        @field:Size(min = 2, message = "이름을 2글자 이상이여야 합니다.")
        @field:Pattern(regexp = "^[가-힣]{2,4}$", message = "이름을 똑바로 입력해주세요.")
        val name: String?,
        @field:NotNull
        @field:Size(min = 9, message = "비밀번호는 최소 9자 이상이어야 합니다.")
        val password: String?
)

class SignInRequest {
    val email: String = ""
    val password: String = ""
}