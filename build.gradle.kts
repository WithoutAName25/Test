import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    id("pl.allegro.tech.build.axion-release") version "1.13.6"
}

scmVersion {
    tag.apply {
        prefix = ""
    }
    nextVersion.apply {
        suffix = "next"
    }
    versionIncrementer("incrementPrerelease")
}

group = "eu.withoutaname.test"
version = scmVersion.version

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}