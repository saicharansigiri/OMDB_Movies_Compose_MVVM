import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}

// Load the keys.properties file
val keystorePropertiesFile = rootProject.file("keys.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "com.example.locomovies"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.locomovies"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(type = "String", name = "OMDB_API_KEY", value = "${keystoreProperties["OMDB_API_KEY"]}")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // view model
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //live data
    implementation (libs.androidx.runtime.livedata)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // retrofit & Gson
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //ok http
    implementation(libs.okhttp)

    //coil
    implementation(libs.coil.compose)

    //lottie
    implementation(libs.lottie.compose)

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}