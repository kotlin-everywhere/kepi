plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("org.kotlin.everywhere.kepi.e01.cli.server.MainKt")
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
    implementation("com.github.kotlin-everywhere.kenet:kenet-server:$kenetVersion")
    implementation("com.github.kotlin-everywhere.kenet:kenet-server-engine-http:$kenetVersion")

    implementation(project(":kepi-02-common"))
}