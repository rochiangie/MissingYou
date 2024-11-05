plugins {
    id("com.android.application") version "8.3.1" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io") // Agregar JitPack como repositorio
        maven {
            url = uri("https://plugins.gradle.org/m2/") // Agregar repositorio para plugins
        }
    }
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io") // Agregar JitPack como repositorio
        maven {
            url = uri("https://plugins.gradle.org/m2/") // Agregar repositorio para plugins
        }
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21") // Mantén la versión de Kotlin
        classpath("com.github.ben-manes:gradle-versions-plugin:0.51.0")
        classpath("com.android.tools.build:gradle:8.3.1")
        classpath(kotlin("gradle-plugin", version = "2.0.21"))// Actualiza a la última versión
    }
}



apply(plugin = "com.android.application")
apply(plugin = "org.jetbrains.kotlin.android")
apply(plugin = "com.github.ben-manes.versions") // Agregar el plugin de versiones
