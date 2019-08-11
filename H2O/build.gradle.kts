plugins {
    java
}

group = "ru.akozlov.leetcode.concurency"
version = "1.0-SNAPSHOT"

tasks.test {
    useJUnitPlatform()
    filter {
        includeTestsMatching("ru.akozlov.leetcode.concurency.H2OTest")
    }
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
