import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
}

//project.name = "service-main"

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
    apply(plugin = "org.springframework.boot")

    println("Enabling Kotlin Spring plugin in project ${project.name}...")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    println("Enabling Spring Boot Dependency Management in project ${project.name}...")
    apply(plugin = "io.spring.dependency-management")

    the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }

    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring") //all-open
//    apply(plugin = "kotlin-jpa")

    val implementation by configurations
    dependencies {
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    }
}