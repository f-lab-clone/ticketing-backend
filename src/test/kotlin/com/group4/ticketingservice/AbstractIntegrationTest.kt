package com.group4.ticketingservice

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.containers.MySQLContainer

@SpringBootTest
@Testcontainers
abstract class AbstractIntegrationTest {

    companion object {
        @Container
        val mysqlContainer = MySQLContainer("mysql:latest")

        @JvmStatic
        @DynamicPropertySource
        fun datasourceConfig(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl)
            registry.add("spring.datasource.username", mysqlContainer::getUsername)
            registry.add("spring.datasource.password", mysqlContainer::getPassword)
    }

    }
}
