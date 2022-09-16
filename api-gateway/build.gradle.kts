//plugins {
//    id("java")
//    id("org.springframework.boot")
//    kotlin("jvm")
//    kotlin("plugin.spring")
//}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:3.1.4")
//    implementation("org.springframework.boot:spring-boot-starter-actuator")
//    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway:3.1.4")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j:2.1.4")
//    implementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner") {
//        exclude("org.springframework.boot:spring-boot-starter-web")
//    }
//    implementation("org.springframework.cloud:spring-cloud-starter-sleuth:3.1.4")
//    implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin:3.1.4")
//    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
//    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}