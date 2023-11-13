package com.group4.ticketingservice.config

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableCaching
class CacheConfig {
    companion object {
        private const val EVENT_CACHE = "getEvents"
    }

    @Bean
    fun cacheManager(): CacheManager {
        val simpleCacheManager = SimpleCacheManager()
        simpleCacheManager.setCaches(
            listOf(
                ConcurrentMapCache(EVENT_CACHE)
            )
        )
        return simpleCacheManager
    }

    @CacheEvict(allEntries = true, value = [EVENT_CACHE])
    @Scheduled(fixedDelay = 10 * 60 * 1000, initialDelay = 500)
    fun cacheEvict() {}
}
