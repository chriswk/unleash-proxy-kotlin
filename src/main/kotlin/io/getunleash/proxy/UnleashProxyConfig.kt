package io.getunleash.proxy

import io.micronaut.context.annotation.ConfigurationProperties
import javax.validation.constraints.NotBlank

@ConfigurationProperties("unleash")
class UnleashProxyConfig {
    @NotBlank
    var url = "http://172.20.0.1:4242/api"

    @NotBlank
    var apiKey = "*:default.ee93de37fa5cc70c3c4e0b59120fa71990ab1d6d0ef23f30ff71de02"

    var proxySecrets: Array<String> = arrayOf("s1")

    var appName: String = "unleash-proxy"

    var environment: String = "dev"

    var fetchInterval: Int = 5000

    var metricsInterval: Int = 30000


    override fun toString(): String {
        return "URL: $url \t apiKey: $apiKey \t secrets: ${proxySecrets.joinToString(separator = ",")} \n appName: $appName"
    }
}
