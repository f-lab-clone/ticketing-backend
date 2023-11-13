package com.group4.ticketingservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class TicketingserviceApplication
fun main(args: Array<String>) {
    runApplication<TicketingserviceApplication>(*args)
}
