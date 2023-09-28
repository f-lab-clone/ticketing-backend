package com.group4.ticketingservice.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class BookmarkFromdto(
    @field:NotNull
    @field:Positive
    var event_id: Int?
)
