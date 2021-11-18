package io.getunleash.proxy

import io.getunleash.Variant

data class FeatureToggleStatus(val name: String, val enabled: Boolean, val variant: Variant? = null)
