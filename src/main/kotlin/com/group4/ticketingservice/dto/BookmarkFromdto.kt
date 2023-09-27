package com.group4.ticketingservice.dto

import jakarta.validation.constraints.NotNull

data class BookmarkFromdto(
        @field:NotNull
        var event_id: Int?
)
