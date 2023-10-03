package com.group4.ticketingservice.config

import com.google.gson.Gson
import com.group4.ticketingservice.filter.LogFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.GsonHttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(private val gson: Gson) : WebMvcConfigurer {
    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>?>) {
        val gsonHttpMessageConverter = GsonHttpMessageConverter()
        gsonHttpMessageConverter.gson = gson
        converters.add(gsonHttpMessageConverter)
    }

    @Bean
    fun loggingFilter(): FilterRegistrationBean<LogFilter> {
        val registrationBean: FilterRegistrationBean<LogFilter> = FilterRegistrationBean<LogFilter>()
        registrationBean.setFilter(LogFilter())
        registrationBean.addUrlPatterns("/*")
        return registrationBean
    }
}
