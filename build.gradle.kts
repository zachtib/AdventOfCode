plugins {
    kotlin("jvm") version "1.9.21"
}

group = "com.zachtib"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}