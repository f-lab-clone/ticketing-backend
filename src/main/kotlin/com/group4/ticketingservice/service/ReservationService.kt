package com.group4.ticketingservice.service

import com.group4.ticketingservice.dto.ReservationFromdto
import com.group4.ticketingservice.entity.Reservation
import com.group4.ticketingservice.repository.ReservationRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ReservationService @Autowired constructor(
    val reservationRepository: ReservationRepository,
    val modelMapper: ModelMapper
) {

    fun create(reservationFormDto: ReservationFromdto): Int? {
        return reservationRepository.save(modelMapper.map(reservationFormDto, Reservation::class.java)).id
    }

    fun get(id: Int): Reservation? {
        return reservationRepository.findByIdOrNull(id.toLong())
    }

    fun delete(id: Int) {
        reservationRepository.deleteById(id.toLong())
    }
    fun getList(): List<Reservation> {
        return reservationRepository.findAll()
    }
}
