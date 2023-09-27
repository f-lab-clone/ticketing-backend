package com.group4.ticketingservice.controller

import com.group4.ticketingservice.dto.BookmarkFromdto
import com.group4.ticketingservice.service.BookmarkService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController // REST API
@RequestMapping("bookmarks")
class BookmarkController @Autowired constructor(val bookmarkService: BookmarkService) {

    // 북마크 등록
    @PostMapping
    fun addBookmark(
        @AuthenticationPrincipal userId: Int,
        @RequestBody @Valid
        boardFormDto: BookmarkFromdto
    ): ResponseEntity<Any> {
        val savedBookmarkId = bookmarkService.create(userId, boardFormDto)
        val headers = HttpHeaders()
        headers.set("Content-Location", "/bookmark/%d".format(savedBookmarkId))
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(savedBookmarkId)
    }

    // 특정 북마크 조회하기
    @GetMapping("/{id}")
    fun getBookmark(@AuthenticationPrincipal userId: Int, @PathVariable id: Int): ResponseEntity<out Any?> {
        try {
            val foundBookmark = bookmarkService.get(userId, id)
            return ResponseEntity.status(HttpStatus.OK).body(foundBookmark ?: "null")
        } catch (e: MethodArgumentNotValidException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    // 북마크 삭제
    @DeleteMapping("/{id}")
    fun deleteBookmark(@AuthenticationPrincipal userId: Int, @PathVariable id: Int): ResponseEntity<Any> {
        bookmarkService.delete(userId, id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    // 로그인한 사용자의 북마크 목록
    @GetMapping()
    fun getBookmarks(@AuthenticationPrincipal userId: Int): ResponseEntity<Any> {
        val bookmarks = bookmarkService.getList(userId)
        return ResponseEntity.status(HttpStatus.OK).body(bookmarks)
    }
}
