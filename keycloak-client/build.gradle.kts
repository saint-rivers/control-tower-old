
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

    implementation("org.springframework.cloud:spring-cloud-starter-config:3.1.3")
    implementation(project(":common"))
}


//    <dependency>
//        <groupId>org.glassfish.jersey.core</groupId>
//        <artifactId>jersey-server</artifactId>
//        <version>2.17</version>
//    </dependency>
//
//    <dependency>
//        <groupId>org.glassfish.jersey.containers</groupId>
//        <artifactId>jersey-container-servlet-core</artifactId>
//        <version>2.17</version>
//    </dependency>