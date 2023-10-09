package com.group4.ticketingservice.controller

import com.group4.ticketingservice.dto.BookmarkFromdto
import com.group4.ticketingservice.entity.Bookmark
import com.group4.ticketingservice.service.BookmarkService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
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

    @PostMapping
    fun addBookmark(
        @AuthenticationPrincipal userId: Int,
        @RequestBody @Valid
        boardFormDto: BookmarkFromdto
    ): ResponseEntity<Any> {
        val savedBookmark: Bookmark = bookmarkService.create(userId, boardFormDto)

        val headers = HttpHeaders()
        headers.set("Content-Location", "/bookmarks/%d".format(savedBookmark.id!!))

        return ResponseEntity(savedBookmark, headers, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getBookmark(
        request: HttpServletRequest,
        @AuthenticationPrincipal userId: Int,
        @PathVariable id: Int
    ): ResponseEntity<out Any?> {
        val foundBookmark = bookmarkService.get(userId, id)

        val headers = HttpHeaders()
        headers.set("Content-Location", request.requestURI)

        return ResponseEntity(foundBookmark, headers, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteBookmark(
        @AuthenticationPrincipal userId: Int,
        @PathVariable id: Int
    ): ResponseEntity<Any> {
        bookmarkService.delete(userId, id)
        return ResponseEntity(null, HttpStatus.OK)
    }

    @GetMapping
    fun getBookmarks(
        request: HttpServletRequest,
        @AuthenticationPrincipal userId: Int,
        @PageableDefault(size = 10) pageable: Pageable
    ): ResponseEntity<Page<Bookmark>> {
        val page = bookmarkService.getBookmarks(userId, pageable)

        val headers = HttpHeaders()
        headers.set("Content-Location", request.requestURI)

        return ResponseEntity(page, headers, HttpStatus.OK)
    }
}
