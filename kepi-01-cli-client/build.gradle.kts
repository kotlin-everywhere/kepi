plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("org.kotlin.everywhere.kepi.e01.cli.client.MainKt")
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
    implementation("com.github.kotlin-everywhere.kenet:kenet-client:$kenetVersion")
    implementation("com.github.kotlin-everywhere.kenet:kenet-client-engine-http:$kenetVersion")
    implementation(project(":kepi-01-cli-common"))

    implementation("com.github.kotlin-everywhere:rpi-sense-hat-java:36a35f5925")
}