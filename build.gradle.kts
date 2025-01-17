// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.1")
        classpath(libs.kotlin.gradle)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
        maven(url = "https://androidx.dev/snapshots/builds/7559387/artifacts/repository/")
        maven(url = "https://dl.bintray.com/amulyakhare/maven") {
            content {
                includeGroup("com.amulyakhare")
            }
        }
        maven(url = "https://pkgs.dev.azure.com/MicrosoftDeviceSDK/DuoSDK-Public/_packaging/Duo-SDK-Feed/maven/v1")
        jcenter() // For draglinearlayout
    }
}

tasks.create<Delete>("clean") {
    delete(rootProject.buildDir)
}