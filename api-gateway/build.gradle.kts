//plugins {
//    id("java")
//    id("org.springframework.boot")
//    kotlin("jvm")
//    kotlin("plugin.spring")
//}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:3.1.3")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway:3.1.3")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j:2.1.4")
//    implementation("org.springdoc:springdoc-openapi-common:1.6.11")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.6.11")

}