
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:3.1.3")
    {
        exclude("javax.ws.rs:jsr311-api")
    }
//    implementation("org.jboss.resteasy:resteasy-client")
//    implementation("org.jboss.resteasy:resteasy-jackson2-provider")
//    implementation("org.jboss.resteasy:resteasy-multipart-provider")
    implementation("org.glassfish.jersey.core:jersey-server:2.17")
    implementation("org.glassfish.jersey.containers:jersey-container-servlet-core:2.17")

    implementation("org.keycloak:keycloak-admin-client:16.1.1")
    implementation("org.keycloak:keycloak-spring-boot-starter:16.1.1")

    implementation("org.springdoc:springdoc-openapi-ui:1.6.11")

    implementation(project(":common"))
}
