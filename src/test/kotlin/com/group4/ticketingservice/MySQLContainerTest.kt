package com.group4.ticketingservice

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.MySQLContainer

// @SpringBootTest()
@TestConfiguration(proxyBeanMethods = false)
class MySQLContainerTest {

    @Bean
    @ServiceConnection
    fun mysqlContainer(): MySQLContainer<*> {
        return MySQLContainer("mysql:latest")
    }

    @Test
    fun printMysqlVersion() {
        val mysqlContainer = mysqlContainer()
        mysqlContainer.start()
        val mysqlVersion = mysqlContainer.execInContainer("mysql", "--version")
        println("MySQL version: $mysqlVersion")
    }
}
