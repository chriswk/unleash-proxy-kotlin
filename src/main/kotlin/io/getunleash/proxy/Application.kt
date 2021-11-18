package io.getunleash.proxy

import io.micronaut.runtime.Micronaut.build
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info = Info(
            title = "unleash-proxy-kotlin",
            version = "0.0"
    )
)
object Api {


}
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("io.getunleash.proxy")
        .start()
}

