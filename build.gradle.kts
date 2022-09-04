plugins {
    kotlin("jvm") version "1.7.10"
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "pw.modder"
version = "1.3.3"

repositories {
    mavenCentral()
}

application {
    mainClass.set("pw.modder.mgt2helper.MainKt")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.formdev:flatlaf:2.4")
    implementation(kotlin("test-junit"))
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks.shadowJar {
    minimize {
        exclude(dependency("com.formdev:flatlaf:.*"))
    }
}