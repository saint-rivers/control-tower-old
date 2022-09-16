
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:3.1.4")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    runtimeOnly("org.postgresql:r2dbc-postgresql")
    testImplementation("io.projectreactor:reactor-test")

    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.6.11")

    implementation(project(":common"))
}