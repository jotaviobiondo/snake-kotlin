plugins {
    kotlin("jvm") version "1.3.71"
}

group = "io.github.jotaviobiondo"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.1")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.0.1")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.0.1")
    testImplementation("io.mockk:mockk:1.9.3")
}


configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    test {
        useJUnitPlatform()
    }
}
