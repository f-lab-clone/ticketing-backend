package com.group4.ticketingservice.controller

import com.group4.ticketingservice.dto.BookmarkFromdto
import com.group4.ticketingservice.service.BookmarkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
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

    // 게시글 읽기
    @GetMapping("/{id}")
    fun getPost(@PathVariable id: Long): ResponseEntity<Any> {
        val post = bookmarkService.getPost(id)
        return ResponseEntity.ok().body(post)
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: Long): ResponseEntity<Any> {
        bookmarkService.deletePost(id)
        return ResponseEntity.ok().body(true)
    }

    // 게시글 목록
    @GetMapping("/list")
    fun listPost(): ResponseEntity<Any> {
        return ResponseEntity.ok().body(bookmarkService.getPostList())
    }
}
