package com.group4.ticketingservice

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
abstract class AbstractIntegrationTest {

    companion object {
        @Container
        val mysqlContainer = MySQLContainer("mysql:latest")
            // .withConfigurationOverride("../../../../../db/mysql/conf.d/my.cnf")
            .withInitScript("init-db.sql") // it refers to src/test/resources/init-db.sql # TODO: combine with db/mysql/initdb.d/init-db.sql

        @JvmStatic
        @DynamicPropertySource
        fun datasourceConfig(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl)
            registry.add("spring.datasource.username", mysqlContainer::getUsername)
            registry.add("spring.datasource.password", mysqlContainer::getPassword)
        }
    }
}
