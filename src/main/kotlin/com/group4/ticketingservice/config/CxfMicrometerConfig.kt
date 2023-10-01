package com.group4.ticketingservice.config

import io.micrometer.config.MicrometerFactoryBeanListener
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.context.annotation.Bean

class CxfMicrometerConfig {

    @Bean
    fun cxfMicrometerBean(registry: MeterRegistry?): MicrometerFactoryBeanListener {
        return MicrometerFactoryBeanListener(registry)
    }
}
