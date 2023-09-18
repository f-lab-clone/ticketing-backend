package com.group4.ticketingservice

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
class TicketingserviceApplication

fun main(args: Array<String>) {
    runApplication<TicketingserviceApplication>(*args)
}

@PostConstruct
fun started() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
}
