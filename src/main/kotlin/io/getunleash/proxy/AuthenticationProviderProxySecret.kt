package io.getunleash.proxy

import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

@Singleton
class AuthenticationProviderProxySecret(val unleashProxyConfig: UnleashProxyConfig) : AuthenticationProvider {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(AuthenticationProviderProxySecret::class.java)
    }

    override fun authenticate(
        httpRequest: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<*, *>?
    ): Publisher<AuthenticationResponse> {
        return if (unleashProxyConfig.proxySecrets.any { it == httpRequest?.headers?.get("Authorization") }) {
            Mono.just(AuthenticationResponse.success("proxyUser"))
        } else {
            Mono.just(AuthenticationResponse.failure("No Authorization header matching proxy secrets were found"))
        }
    }
}
