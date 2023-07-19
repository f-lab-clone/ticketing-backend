package com.group4.ticketingservice.service

import com.group4.ticketingservice.dto.UserFromdto
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.TestRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TestService @Autowired constructor(
    val testRepository: TestRepository,
    val modelMapper: ModelMapper
) {

    fun create(userFromdto: UserFromdto): Int? {
        return testRepository.save(modelMapper.map(userFromdto, User::class.java)).id
    }

    fun getList(): List<User> {
        return testRepository.findAll()
    }
}
