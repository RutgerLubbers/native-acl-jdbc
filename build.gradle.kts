group = "com.ilionx.poc"
version = "0.0.1-SNAPSHOT"

val javaVersion = JavaVersion.VERSION_17
plugins {
    val springBootVersion = "3.1.1"
    val springDependencyManagementVersion = "1.1.1"

    java

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springDependencyManagementVersion

    // id("io.beekeeper.gradle.plugins.dependency-updates") version "0.14.1"

    id("org.graalvm.buildtools.native") version "0.9.23"
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
    implementation("org.postgresql:postgresql:42.6.0")

    implementation("com.h2database:h2:2.2.220")
}
