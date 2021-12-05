plugins {
    kotlin("jvm")
    id("org.jetbrains.compose") version "1.0.0"
    application
}

application {
    mainClass.set("org.kotlin.everywhere.kepi.e02.server.MainKt")
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
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
    implementation(compose.desktop.currentOs)
    implementation(compose.desktop.macos_x64)
    implementation(compose.desktop.macos_x64)
    implementation(compose.desktop.windows_x64)

    implementation(project(":kepi-02-common"))
}