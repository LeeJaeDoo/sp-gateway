import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val snippetsDir: String by extra("build/generated-snippets")
val coroutineVersion: String by extra { "1.3.9" }
val springRestdocsVersion: String by extra { "2.0.4.RELEASE" }
val springCloudVersion: String by extra { "Hoxton.SR8" }

plugins {
    val kotlinVersion = "1.4.30"
    val springBootVersion = "2.3.3.RELEASE"
    val springDependencyManagementVersion = "1.0.11.RELEASE"

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springDependencyManagementVersion

    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
}

group = "com.sp"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    implementation("com.auth0:java-jwt:3.11.0")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.2")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
