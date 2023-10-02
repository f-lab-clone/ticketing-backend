package com.group4.ticketingservice.utils

import com.group4.ticketingservice.dto.EventResponse
import com.group4.ticketingservice.dto.SuccessResponseDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.MethodParameter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import org.springframework.web.util.UriComponentsBuilder
import com.group4.ticketingservice.entity.Event

@RestControllerAdvice
class ResponseAdvice<T>(
    @Value("\${spring.data.web.pageable.one-indexed-parameters}")
    private val oneIndexed: Boolean
) : ResponseBodyAdvice<T> {

    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>>
    ): Boolean {
        return true
    }

    override fun beforeBodyWrite(
        body: T?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): T? {
        if (body !is Page<*>) {
            return SuccessResponseDTO(
                data = body as Any,
                path = response.headers.getFirst("Content-Location")
            ) as T?
        }

        val page = PageImpl(body.content, body.pageable, body.totalElements)

        val headers = response.headers
        headers.set(
            "Access-Control-Expose-Headers",
            "Link,Page-Number,Page-Size,Total-Elements,Total-Pages"
        )

        val links = page.links(request)
        if (links.isNotBlank()) {
            headers.set("Link", links)
        }

        val pageNumber = if (oneIndexed) {
            page.number.plus(1)
        } else {
            page.number
        }

        headers.set("Page-Number", pageNumber.toString())
        headers.set("Page-Size", page.size.toString())
        headers.set("Total-Elements", page.totalElements.toString())
        headers.set("Total-Pages", page.totalPages.toString())

        var data = page.content

        if (data[0] is Event) {
            data = (page.content as List<Event>).map {
                EventResponse(
                    id = it.id!!,
                    title = it.title,
                    date = it.date,
                    reservationStartTime = it.reservationStartTime,
                    reservationEndTime = it.reservationEndTime,
                    maxAttendees = it.maxAttendees
                )
            }
        }

        return SuccessResponseDTO(
            data = data,
            path = response.headers.getFirst("Content-Location"),
            totalElements = page.totalElements
        ) as T?
        // return page as T?
    }

    private fun PageImpl<*>.links(request: ServerHttpRequest): String {
        val links = mutableListOf<String>()
        val builder = UriComponentsBuilder.fromUri(request.uri)
        if (request.uri.host == "localhost") {
            builder.port(request.uri.port)
        }

        if (!this.isFirst) {
            val link = builder.replacePageAndSize(this.pageable.first())
            links.add("<${link.toUriString()}>; rel=\"first\"")
        }

        if (this.hasPrevious()) {
            val link = builder.replacePageAndSize(this.previousPageable())
            links.add("<${link.toUriString()}>; rel=\"prev\"")
        }

        if (this.hasNext()) {
            val link = builder.replacePageAndSize(this.nextPageable())
            links.add("<${link.toUriString()}>; rel=\"next\"")
        }

        if (!this.isLast) {
            val last = builder.cloneBuilder()
            last.replaceQueryParam("page", this.totalPages)
            last.replaceQueryParam("size", this.size)

            links.add("<${last.toUriString()}>; rel=\"last\"")
        }

        return links.joinToString(",")
    }

    private fun UriComponentsBuilder.replacePageAndSize(
        page: Pageable
    ): UriComponentsBuilder {
        val builder = this.cloneBuilder()

        val pageNumber = if (oneIndexed) {
            page.pageNumber.plus(1)
        } else {
            page.pageNumber
        }
        builder.replaceQueryParam("page", pageNumber)
        builder.replaceQueryParam("size", page.pageSize)

        return builder
    }
}
