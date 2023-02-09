group = "com.ilionx.poc"
version = "0.0.1-SNAPSHOT"

val javaVersion = JavaVersion.VERSION_17
plugins {
    val springBootVersion = "3.0.2"
    val springDependencyManagementVersion = "1.1.0"

    java

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springDependencyManagementVersion

    id("org.graalvm.buildtools.native") version "0.9.18"
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

dependencies {

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-acl")

    implementation("com.zaxxer:HikariCP")
    implementation("org.postgresql:postgresql:42.5.1")

    // implementation("com.h2database:h2:2.1.214")
}
