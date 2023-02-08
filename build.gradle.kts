group = "com.ilionx.poc"
version = "0.0.1-SNAPSHOT"

val javaVersion = JavaVersion.VERSION_17

// dependency versions
val datasourceProxyVersion = "1.8.1"
val hawaiiFrameworkVersion = "6.0.0.BUILD-SNAPSHOT"
val postgresqlVersion = "42.5.1"

buildscript {
    // Set the Artifactory from an environment variable, with a default that works for local development.
    extra.set(
            "artifactoryUrl", System.getenv("ARTIFACTORY_URL")
            ?: "https://artifactory.ilionx.cloud/artifactory/repo/"
    )
    println("[build] Using artifactoryUrl: ${extra["artifactoryUrl"]}")


    // Do not add new repositories here. The artifactory repository here acts as a proxy/cache. Add missing repos to the artifactory
    // instance in case you are missing something. Note that this line is replaced by the ansible/jenkins scripts to make sure the code
    // runs on these environments.
    repositories {

        maven {
            name = "build-script-artifactory"

            url = uri("${extra["artifactoryUrl"]}")
            if (System.getenv("ARTIFACTORY_PWD") != null) {
                println("[build] Using secure artifactory.")
                credentials {
                    username = System.getenv("ARTIFACTORY_UID")
                    password = System.getenv("ARTIFACTORY_PWD")
                }
                authentication {
                    create<BasicAuthentication>("basic")
                }

            }
            metadataSources {
                mavenPom()
                artifact()
                ignoreGradleMetadataRedirection()
            }
        }
        mavenLocal()
    }
}

plugins {
    val springBootVersion = "3.0.2"
    val springDependencyManagementVersion = "1.1.0"

    // java adds the gradle java tasks.
    java
    // enables:
    // `jvm-test-suite`
    // `test-report-aggregation`
    // See See https://docs.gradle.org/current/userguide/jvm_test_suite_plugin.html

    // Our tests are using groovy with the spock framework, this adds the compileTestGroovy task and the watch to the test/groovy folder
    groovy

    idea

    // the spring boot plugin reacts to other plugins such as the java plugin.
    // In case dependency management is also available, the Spring Bill Of Materials will be injected containing versions of spring libraries.
    // see https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/
    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springDependencyManagementVersion

    /*
     * The project-report plugin provides file reports on dependencies, tasks, etc.
     * See https://docs.gradle.org/current/userguide/project_report_plugin.html.
     */
    `project-report`

    id("com.github.spotbugs") version "5.0.13"
    id("org.graalvm.buildtools.native") version "0.9.18"
}

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

tasks.withType<JavaCompile> {
    // defaults to use the platform encoding
    options.encoding = Charsets.UTF_8.name()
}

repositories {
    maven {
        name = "artifactory"

        url = uri("${extra["artifactoryUrl"]}")
        if (System.getenv("ARTIFACTORY_PWD") != null) {
            println("[build] Using secure artifactory.")
            if (System.getenv("ARTIFACTORY_PWD") != null) {
                credentials {
                    username = System.getenv("ARTIFACTORY_UID")
                    password = System.getenv("ARTIFACTORY_PWD")
                }
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }

        }
        metadataSources {
            mavenPom()
            artifact()
            ignoreGradleMetadataRedirection()
        }
    }

    mavenLocal()
    mavenCentral()
}

dependencies {

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.security:spring-security-acl")

    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.postgresql:postgresql:${postgresqlVersion}")

    implementation("org.hawaiiframework:hawaii-starter-boot:${hawaiiFrameworkVersion}")
    implementation("net.ttddyy:datasource-proxy:${datasourceProxyVersion}")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

}
