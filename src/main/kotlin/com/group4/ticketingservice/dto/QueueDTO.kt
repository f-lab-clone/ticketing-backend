package com.group4.ticketingservice.dto

class QueueResponseDTO {
    val status: Boolean = false
    val message: String = ""
    val data: TicketInfo? = null
}
class TicketInfo {
    var eventId: String = ""
    var userId: String = ""
    var isWaiting = true
    var offset = 0
}
data class TicketRequest(
    val eventId: Int,
    val userId: Int
)
