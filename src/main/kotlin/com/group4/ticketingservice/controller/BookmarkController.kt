package com.group4.ticketingservice.controller

import com.group4.ticketingservice.dto.BookmarkFromdto
import com.group4.ticketingservice.service.BookmarkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController // REST API
@RequestMapping("bookmarks")
class BookmarkController @Autowired constructor(val bookmarkService: BookmarkService) {

    // 북마크 등록
    @PostMapping
    fun addBookmark(boardFormDto: BookmarkFromdto): ResponseEntity<Any> {
        val savedBookmarkId = bookmarkService.create(boardFormDto)
        val headers = HttpHeaders()
        headers.set("Content-Location", "/bookmark/%d".format(savedBookmarkId))
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(savedBookmarkId)
    }

    // 특정 북마크 조회하기
    @GetMapping("/{id}")
    fun getBookmark(@PathVariable id: Long): ResponseEntity<Any> {
        try {
            val foundBookmark = bookmarkService.get(id)
            return ResponseEntity.status(HttpStatus.OK).body(foundBookmark)
        } catch (e: MethodArgumentNotValidException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        } catch (e: NoSuchElementException) {
            return ResponseEntity.status(HttpStatus.OK).body(null)
        }
    }

    // 북마크 삭제
    @DeleteMapping("/{id}")
    fun deleteBookmark(@PathVariable id: Long): ResponseEntity<Any> {
        bookmarkService.delete(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    // 전체사용자 북마크 목록
    @GetMapping()
    fun getBookmarks(): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.OK).body(bookmarkService.getList())
    }
}
