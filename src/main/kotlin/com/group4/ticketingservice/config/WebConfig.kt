package com.group4.ticketingservice.config

import com.google.gson.Gson
import com.group4.ticketingservice.utils.logging.LogInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.GsonHttpMessageConverter
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(private val gson: Gson) : WebMvcConfigurer {
    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>?>) {
        val gsonHttpMessageConverter = GsonHttpMessageConverter()
        gsonHttpMessageConverter.gson = gson
        converters.add(gsonHttpMessageConverter)
    }
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LogInterceptor())
    }
}
