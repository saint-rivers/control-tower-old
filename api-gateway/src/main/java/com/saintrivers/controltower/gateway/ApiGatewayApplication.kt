package com.saintrivers.controltower.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@SpringBootApplication
@EnableEurekaClient
class ApiGatewayApplication {

//    @Bean
//    fun routes(builder: RouteLocatorBuilder): RouteLocator = builder
//        .routes()
//        .route { p ->
//            p.path("/get")
//                .filters {
//                    it.addRequestHeader("hello", "world")
//                }
//                .uri("http://httpbin.org:80")
//        }
//        .route { p ->
//            p.host("*.circuitbreaker.com")
//                .filters {
//                    it.circuitBreaker { config ->
//                        config.name = "mycmd"
//                        config.fallbackUri = URI.create("forward:/fallback")
//                    }
//                }
//                .uri("http://httpbin.org:80")
//        }
//        .build()
}

@RestController
class FallbackController {
    @GetMapping("/fallback")
    fun fallback(): Mono<String> {
        return Mono.just("fallback")
    }
}

fun main(args: Array<String>) {
    runApplication<ApiGatewayApplication>(*args)
}
