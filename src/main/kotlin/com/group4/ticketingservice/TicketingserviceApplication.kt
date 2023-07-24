package com.group4.ticketingservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
class TicketingserviceApplication

fun main(args: Array<String>) {
    runApplication<TicketingserviceApplication>(*args)
}
