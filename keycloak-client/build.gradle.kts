plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
}

group = "com.saintrivers.controltower"
version = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:3.1.4")
    {
        exclude("javax.ws.rs:jsr311-api")
    }
    implementation("org.jboss.resteasy:resteasy-client")
    implementation("org.jboss.resteasy:resteasy-jackson2-provider")
    implementation("org.jboss.resteasy:resteasy-multipart-provider")

    implementation("org.keycloak:keycloak-admin-client:16.1.1")
    implementation("org.keycloak:keycloak-spring-boot-starter:16.1.1")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springdoc:springdoc-openapi-ui:1.6.11")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}