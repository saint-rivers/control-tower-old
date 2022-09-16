import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("org.springframework.boot") version "2.7.3" apply false
    id("io.spring.dependency-management") version "1.0.13.RELEASE" apply false
    kotlin("jvm") version "1.6.21" apply false
    kotlin("plugin.spring") version "1.6.21" apply false
}

allprojects {

    group = "com.saintrivers.controltower"
    version = "1.0.1"

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {
    repositories {
        mavenCentral()
    }

    apply(plugin = "kotlin")

    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
    }

    val implementation by configurations
    val testImplementation by configurations
    val testRuntimeOnly by configurations
    val developmentOnly by configurations

    dependencies {
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        developmentOnly("org.springframework.boot:spring-boot-devtools")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
        implementation("org.springframework.cloud:spring-cloud-starter-sleuth:3.1.3")
        implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin:3.1.3")
        implementation("org.springframework.boot:spring-boot-starter-actuator")
    }
}