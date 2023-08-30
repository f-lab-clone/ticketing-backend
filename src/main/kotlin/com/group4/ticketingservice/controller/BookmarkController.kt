package com.group4.ticketingservice.controller

import com.group4.ticketingservice.dto.BookmarkFromdto
import com.group4.ticketingservice.service.BookmarkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.transaction.annotation.Transactional
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
    fun addBookmark(@AuthenticationPrincipal username: String, boardFormDto: BookmarkFromdto): ResponseEntity<Any> {
        val savedBookmarkId = bookmarkService.create(username, boardFormDto)
        val headers = HttpHeaders()
        headers.set("Content-Location", "/bookmark/%d".format(savedBookmarkId))
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(savedBookmarkId)
    }

    // 특정 북마크 조회하기
    @GetMapping("/{id}")
    fun getBookmark(@AuthenticationPrincipal username: String, @PathVariable id: Int): ResponseEntity<out Any?> {
        try {
            val foundBookmark = bookmarkService.get(username, id)
            return ResponseEntity.status(HttpStatus.OK).body(foundBookmark ?: "null")
        } catch (e: MethodArgumentNotValidException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    // 북마크 삭제
    @DeleteMapping("/{id}")
    @Transactional
    fun deleteBookmark(@AuthenticationPrincipal username: String, @PathVariable id: Int): ResponseEntity<Any> {
        bookmarkService.delete(username, id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    // 로그인한 사용자의 북마크 목록
    @GetMapping()
    fun getBookmarks(@AuthenticationPrincipal username: String): ResponseEntity<Any> {
        val bookmarks = bookmarkService.getList(username)
        return ResponseEntity.status(HttpStatus.OK).body(bookmarks)
    }
}
