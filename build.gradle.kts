plugins {
    kotlin("jvm") version "2.0.0"
}

group = "me.grian"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.github.classgraph:classgraph:4.8.174")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}