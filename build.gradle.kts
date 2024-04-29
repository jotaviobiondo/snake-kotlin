plugins {
    kotlin("jvm") version "1.9.23"
}

group = "io.github.jotaviobiondo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // todo: remove kotest
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.6.3")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.6.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}