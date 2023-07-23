package com.group4.ticketingservice.service

import com.group4.ticketingservice.dto.BookmarkFromdto
import com.group4.ticketingservice.entity.Bookmark
import com.group4.ticketingservice.repository.BookmarkRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BookmarkService @Autowired constructor(
    val bookmarkRepository: BookmarkRepository,
    val modelMapper: ModelMapper
) {

    fun create(bookmarkFormDto: BookmarkFromdto): Int? {
        return bookmarkRepository.save(modelMapper.map(bookmarkFormDto, Bookmark::class.java)).id
    }

    fun get(id: Int): Bookmark? {
        return bookmarkRepository.findByIdOrNull(id.toLong())
    }

    fun delete(id: Int) {
        bookmarkRepository.deleteById(id.toLong())
    }

    fun getList(): List<Bookmark> {
        return bookmarkRepository.findAll()
    }
}
