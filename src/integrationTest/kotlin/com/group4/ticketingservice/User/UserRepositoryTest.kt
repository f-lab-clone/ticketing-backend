package com.group4.ticketingservice.User

import com.group4.ticketingservice.AbstractIntegrationTest
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.utils.Authority
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.TestPropertySource

@TestPropertySource(properties = ["spring.jpa.hibernate.ddl-auto=create"])
class UserRepositoryTest(
    @Autowired val userRepository: UserRepository
) : AbstractIntegrationTest() {
    object testFields {
        const val testName = "minjun"
        const val testUserName = "minjun3021@qwer.com"
        const val password = "1234"
    }

    val sampleUser = User(
        name = testFields.testName,
        email = testFields.testUserName,
        password = BCryptPasswordEncoder().encode(testFields.password),
        authority = Authority.USER
    )

    @AfterEach fun removeUser() {
        userRepository.deleteAll()
    }

    @Test
    fun `userRepository_save should return savedUser`() {
        // when
        val savedUser = userRepository.save(sampleUser)
        // then
        assertThat(savedUser).isEqualTo(sampleUser)
    }

    @Test
    fun `userRepository_findByEmail return saveUser when user exist`() {
        // given
        userRepository.save(sampleUser)
        // when
        val savedUser = userRepository.findByEmail(sampleUser.email)
        // then
        assertThat(savedUser?.email).isEqualTo(sampleUser.email)
    }

    @Test
    fun `userRepository_findByEmail return null when user not exist`() {
        // given

        // when
        val savedUser = userRepository.findByEmail(sampleUser.email)
        // then
        assertThat(savedUser?.email).isEqualTo(null)
    }

    @Test
    fun `userRepository_existByEmail return true when user exist`() {
        // given
        userRepository.save(sampleUser)
        // when
        val isUserExist = userRepository.existsByEmail(sampleUser.email)
        // then
        assertThat(isUserExist).isEqualTo(true)
    }

    @Test
    fun `userRepository_existByEmail return false when user not exist`() {
        // given

        // when
        val isUserExist = userRepository.existsByEmail(sampleUser.email)
        // then
        assertThat(isUserExist).isEqualTo(false)
    }
}
