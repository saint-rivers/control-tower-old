package com.saintrivers.controltower.gateway.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class FallbackController {
    @GetMapping("/fallback")
    fun fallback(): Mono<String> {
        return Mono.just("fallback")
    }
}