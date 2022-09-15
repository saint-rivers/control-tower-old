package com.saintrivers.controltower.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

//    @Bean
//    fun corsConfigurationSource(): CorsConfigurationSource {
//        val configuration = CorsConfiguration()
//        configuration.allowedOrigins = listOf("*")
//        configuration.allowedMethods = listOf("GET", "POST")
//        val source = UrlBasedCorsConfigurationSource()
//        source.registerCorsConfiguration("/**", configuration)
//        return source
//    }
//   @Bean
//    fun addCorsConfig(): WebMvcConfigurer {
//        return object : WebMvcConfigurer {
//            override fun addCorsMappings(registry: CorsRegistry) {
//                registry.addMapping("/**")
//                    .allowedMethods("*")
//                    .allowedOriginPatterns("*")
//                    .allowedHeaders("Authorization", "Content-Type", "x-requested-with", "X-XSRF-TOKEN")
//                    .allowCredentials(true)
//            }
//        }
//    }

    @Bean
    fun securityFilter(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http.invoke {
            authorizeExchange {
                authorize("/**", permitAll)
            }
            cors { }
            csrf { disable() }
            oauth2ResourceServer {
                jwt { }
            }
        }
    }
}