package com.group4.ticketingservice.controller

import com.group4.ticketingservice.dto.BookmarkFromdto
import com.group4.ticketingservice.service.BookmarkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController // REST API
@RequestMapping("bookmark")
class BookmarkController @Autowired constructor(val bookmarkService: BookmarkService) {

    // 북마크 등록
    @PostMapping
    fun addBookmark(boardFormDto: BookmarkFromdto): ResponseEntity<Any> {
        val save = bookmarkService.create(boardFormDto)
        return ResponseEntity.ok().body(save)
    }

    // 특정 북마크 조회하기
    @GetMapping("/{id}")
    fun getBookmark(@PathVariable id: Long): ResponseEntity<Any> {
        try {
            val post = bookmarkService.get(id)
            return ResponseEntity.ok().body(post)
        } catch (e: MethodArgumentNotValidException) {
            return ResponseEntity.badRequest().build()
        } catch (e: NoSuchElementException) {
            return ResponseEntity.notFound().build()
        }
    }

    // 북마크 삭제
    @DeleteMapping("/{id}")
    fun deleteBookmark(@PathVariable id: Long): ResponseEntity<Any> {
        bookmarkService.delete(id)
        return ResponseEntity.ok().body(true)
    }

    // 전체사용자 북마크 목록
    @GetMapping("/list")
    fun getBookmarks(): ResponseEntity<Any> {
        return ResponseEntity.ok().body(bookmarkService.getList())
    }
}
