plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}


repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "16"
    }
}

dependencies {
    // kenet
    val kenetVersion = "dbd92cf3c8"
    implementation("com.github.kotlin-everywhere.kenet:kenet-dsl:$kenetVersion")
}