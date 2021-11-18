package io.getunleash.proxy

import io.getunleash.Unleash
import io.getunleash.UnleashContext
import io.getunleash.Variant
import jakarta.inject.Singleton

@Singleton
class ProxyClient(val unleash: Unleash) {
    val defaultVariant = Variant("Disabled", "", false)

    fun getEnabledToggles(context: UnleashContext): List<FeatureToggleStatus> {
        return unleash.more().featureToggleNames.map { name ->
            FeatureToggleStatus(name = name, unleash.isEnabled(name, context, false), variant = unleash.getVariant(name, context, defaultVariant))
        }.filter { it.enabled }
    }
}
