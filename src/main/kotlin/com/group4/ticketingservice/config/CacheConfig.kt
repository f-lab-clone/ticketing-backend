package com.group4.ticketingservice.config

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class CacheConfig {
    @Bean
    fun cacheManager(): CacheManager {
        val simpleCacheManager = SimpleCacheManager()
        simpleCacheManager.setCaches(
            listOf(
                ConcurrentMapCache("getEvents")
            )
        )
        return simpleCacheManager
    }
}
