plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.com.google.gms.google.services)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.org.jetbrains.kotlin.plugin.compose)
}

android {
    namespace = "com.mjramirez.openweatherapp"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.mjramirez.openweatherapp"
        minSdk = 29
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    val composeBom = platform("androidx.compose:compose-bom:2024.06.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material.icons.extended)

    // Firebase Auth
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    // Google Play Services location
    implementation(libs.play.services.location)

    // Retrofit & Gson
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
}