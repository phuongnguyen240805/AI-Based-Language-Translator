plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.aibasedlanguagetranslator"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.aibasedlanguagetranslator"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10" // hoặc version phù hợp với Compose
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation (libs.androidx.compiler)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.animation.core.android)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.material3.v120)
    implementation(libs.androidx.ui.v161)
    implementation(libs.translate)
    implementation(libs.androidx.core.ktx.v1120)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.ktx)

    implementation (platform(libs.androidx.compose.bom))

    // Compose UI
    implementation (libs.ui.v161)
    implementation (libs.material3.v120) // Material 3

    // Navigation for Jetpack Compose
    implementation (libs.androidx.navigation.compose)

    // Tooling preview (cho @Preview)
    implementation (libs.androidx.ui.tooling.preview.v161)
    debugImplementation (libs.androidx.ui.tooling.v161)

    // Foundation (Layout như Column, Row, Box, Scroll...)
    implementation (libs.androidx.foundation)

    // Optional: Icons Extended (nhiều icon hơn)
    implementation (libs.material.icons.extended)

    // call API từ LibreTranslate
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
}