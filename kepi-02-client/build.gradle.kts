plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("org.kotlin.everywhere.kepi.e02.client.MainKt")
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
    val kenetVersion = "087fed6fea"
    implementation("com.github.kotlin-everywhere.kenet:kenet-client:$kenetVersion")
    implementation("com.github.kotlin-everywhere.kenet:kenet-client-engine-http:$kenetVersion")
    implementation(project(":kepi-02-common"))

    implementation("com.github.kotlin-everywhere:rpi-sense-hat-java:36a35f5925")
}