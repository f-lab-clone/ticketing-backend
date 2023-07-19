package com.group4.ticketingservice.service

import com.group4.ticketingservice.entity.Test
import com.group4.ticketingservice.repository.TestRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TestService @Autowired constructor(
    val testRepository: TestRepository,
    val modelMapper: ModelMapper
) {
    fun getList(): List<Test> {
        return testRepository.findAll()
    }
}
