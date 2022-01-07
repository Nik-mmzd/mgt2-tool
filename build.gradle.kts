plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "pw.modder"
version = "1.1.0"

repositories {
    mavenCentral()
}

application {
    mainClass.set("pw.modder.mgt2helper.MainKt")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.charleskorn.kaml:kaml:0.38.0")
    implementation("com.formdev:flatlaf:2.0-rc1")
}

tasks.shadowJar {
    minimize {
        exclude(dependency("com.formdev:flatlaf:.*"))
    }
}