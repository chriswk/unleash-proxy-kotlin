package io.getunleash.proxy

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.HttpStatus.UNAUTHORIZED
import io.micronaut.http.HttpStatus.OK
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.assertThrows
import io.micronaut.http.client.HttpClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@MicronautTest(packages = ["io.getunleash.proxy"])
class ProxyAuthTest {
    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun accessingASecuredUrlWithoutAuthorizationHeaderReturnsUnauthorized() {
        val e = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange<Any, Any>(HttpRequest.GET<Any>("/proxy").accept("application/json"))
        }
        assertThat(e.status).isEqualTo(UNAUTHORIZED)
    }

    @Test
    fun accessingUsingOneOfTheConfiguredProxySecretsShouldReturnOk() {
        val answer = client.toBlocking().exchange<Any, Any>(HttpRequest.GET<Any>("/proxy").accept("application/json").header("Authorization", "s1"))
        assertThat(answer.status).isEqualTo(OK)
    }
}
