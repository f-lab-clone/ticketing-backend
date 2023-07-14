package com.group4.ticketingservice.service

import com.group4.ticketingservice.dto.BookmarkFromdto
import com.group4.ticketingservice.model.Bookmark
import com.group4.ticketingservice.repository.BookmarkRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookmarkService @Autowired constructor(
    val bookmarkRepository: BookmarkRepository,
    val modelMapper: ModelMapper
) {

    fun create(boardFormDto: BookmarkFromdto): Int? {
        return bookmarkRepository.save(modelMapper.map(boardFormDto, Bookmark::class.java)).id
    }

    fun get(id: Long): Bookmark? {
        return bookmarkRepository.findById(id).get()
    }

    fun delete(id: Long) {
        bookmarkRepository.deleteById(id)
    }
    fun getList(): List<Bookmark> {
        return bookmarkRepository.findAll()
    }
}
