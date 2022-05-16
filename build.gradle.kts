import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import pl.allegro.tech.build.axion.release.domain.hooks.HookContext
import pl.allegro.tech.build.axion.release.domain.scm.ScmPosition

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
    hooks.apply {
        pre(
            "fileUpdate",
            mapOf(
                "file" to "README.md",
                "pattern" to KotlinClosure2(
                    { v: String, _: HookContext ->
                        "Current version: $v"
                    }
                ),
                "replacement" to KotlinClosure2(
                    { v: String, _: HookContext ->
                        "Current version: $v"
                    }
                )
            )
        )
        pre("commit", KotlinClosure2(
            { v: String, _: ScmPosition ->
                "Release the new version $v"
            }
        ))
    }
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