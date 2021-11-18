package io.getunleash.proxy

import io.getunleash.UnleashContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import org.slf4j.Logger
import org.slf4j.LoggerFactory

data class ProxyResponse(val toggles: List<FeatureToggleStatus>)

@Controller("/proxy")
@Secured(SecurityRule.IS_AUTHENTICATED)
class ProxyController(private val proxyClient: ProxyClient) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(ProxyController::class.java)

    }
    @Get
    fun get(request: HttpRequest<*>): ProxyResponse {
        val context = request.parameters.fold(UnleashContext.builder()) { b, (k, v) ->
            b.addProperty(k, v.toString())
        }.build()
        return ProxyResponse(toggles = proxyClient.getEnabledToggles(context))
    }
}
