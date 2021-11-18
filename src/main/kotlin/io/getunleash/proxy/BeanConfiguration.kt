package io.getunleash.proxy

import io.getunleash.DefaultUnleash
import io.getunleash.Unleash
import io.getunleash.util.UnleashConfig
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.security.authentication.AuthenticationProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Factory
class BeanConfiguration {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(BeanConfiguration::class.java)
    }

    @Bean
    fun unleashConfig(unleashProxyConfig: UnleashProxyConfig): UnleashConfig {
        logger.info("With config: {}", unleashProxyConfig)
        return UnleashConfig.builder()
            .unleashAPI(unleashProxyConfig.url)
            .appName("unleash-proxy-kotlin")
            .customHttpHeader("Authorization", unleashProxyConfig.apiKey)
            .build()
    }

    @Bean
    fun unleashClient(unleashConfig: UnleashConfig): Unleash {
        return DefaultUnleash(unleashConfig)
    }
}
