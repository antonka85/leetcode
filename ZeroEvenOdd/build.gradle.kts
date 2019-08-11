plugins {
    java
}

group = "ru.akozlov.leetcode.concurrency"
version = "1.0-SNAPSHOT"

tasks.test {
    useJUnitPlatform()
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testCompile("org.junit.jupiter", "junit-jupiter-api", "5.5.1")
    testCompile("org.junit.jupiter", "junit-jupiter-engine", "5.5.1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}