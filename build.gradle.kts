plugins {
    kotlin("jvm") version "1.5.31" apply false
    kotlin("plugin.serialization") version "1.5.31" apply false
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}