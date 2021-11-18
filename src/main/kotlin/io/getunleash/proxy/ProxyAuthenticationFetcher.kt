package io.getunleash.proxy

import io.micronaut.context.annotation.Context
import io.micronaut.core.async.publisher.Publishers
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.authentication.Authenticator
import io.micronaut.security.filters.AuthenticationFetcher
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux

@Context
class ProxyAuthenticationFetcher(private val authenticator: Authenticator) : AuthenticationFetcher {
    override fun fetchAuthentication(request: HttpRequest<*>?): Publisher<Authentication> {
        return if (request?.headers?.get("Authorization") != null) {
            val authenticationResponse = Flux.from(authenticator.authenticate(request, null))
            authenticationResponse.switchMap { r ->
                if (r.isAuthenticated && r.authentication.isPresent) {
                    Flux.just(r.authentication.get())
                } else {
                    Publishers.empty()
                }
            }
        } else {
            Publishers.empty()
        }
    }
}
