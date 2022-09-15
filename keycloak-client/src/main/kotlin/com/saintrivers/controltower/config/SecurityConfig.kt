package com.saintrivers.controltower.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.web.servlet.invoke
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@EnableWebSecurity
@Configuration
class SecurityConfig {

    /**
     * with spring mvc, this is the configuration you need to update the CORS config
     * @see <a href="https://medium.com/@tcbasche/cors-in-spring-boot-with-kotlin-55eb5385f0e">Reference</a>
     */
    @Bean
    fun addCorsConfig(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedMethods("*")
                    .allowedOriginPatterns("http://localhost:7070")
                    .allowedHeaders("Authorization", "Content-Type", "x-requested-with", "X-XSRF-TOKEN")
                    .allowCredentials(true)
            }
        }
    }

    @Bean
    fun securityFilter(http: HttpSecurity): SecurityFilterChain {
        http {
            cors {}
            csrf { disable() }
            authorizeRequests {
//                authorize("/api/user/**", authenticated)
//                authorize("/api/role/**", authenticated)
//                authorize("/api/group/**", authenticated)
//                authorize("/actuator/**", permitAll)
//                authorize("/swagger-ui/**", permitAll)
//                authorize("/docs/**", permitAll)
//                authorize("/swagger-ui.html", permitAll)
                authorize("/**", permitAll)
            }
            oauth2ResourceServer {
                jwt {}
            }
        }
        return http.build()
    }
}