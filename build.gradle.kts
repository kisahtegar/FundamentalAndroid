// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
//    id("com.google.devtools.ksp") version "1.8.22-1.0.11" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.11" apply false
//    id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false
}