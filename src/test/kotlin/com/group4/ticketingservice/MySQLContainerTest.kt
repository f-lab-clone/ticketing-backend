// package com.group4.ticketingservice

// import org.junit.jupiter.api.Test
// import org.springframework.beans.factory.annotation.Autowired
// import org.springframework.boot.test.context.TestConfiguration
// import org.springframework.boot.testcontainers.service.connection.ServiceConnection
// import org.springframework.context.annotation.Bean
// import org.springframework.test.web.servlet.MockMvc
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
// import org.testcontainers.containers.MySQLContainer

// // @SpringBootTest()
// @TestConfiguration(proxyBeanMethods = false)
// class MySQLContainerTest {

//     // @Bean
//     // @ServiceConnection
//     // fun mysqlContainer(): MySQLContainer<*> {
//     //     return MySQLContainer("mysql:latest")
//     // }

//     // @Test
//     // fun printMysqlVersion() {
//     //     val mysqlContainer = mysqlContainer()
//     //     mysqlContainer.start()
//     //     val mysqlVersion = mysqlContainer.execInContainer("mysql", "--version")
//     //     println("MySQL version: $mysqlVersion")
//     // }

//     // @Autowired
//     // private lateinit var mockMvc: MockMvc

//     // @Test
//     // fun testMysql() {
//     //     // mockMvc.perform(MockMvcRequestBuilders.get("/set"))
//     //         .andExpect(status().isOk)
//     //         // .andExpect(jsonPath("$[0].user_id").value(sampleBookmark.user_id))
//     //     mockMvc.perform(MockMvcRequestBuilders.get("/list"))
//     //         .andExpect(status().isOk)
//     //         // .andExpect(jsonPath("$[0].user_id").value(sampleBookmark.user_id))
//     // }
// }
