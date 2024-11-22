import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    //Parcelize
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.alexius.newsery2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.alexius.newsery2"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = gradleLocalProperties(rootDir, providers = project.providers)
        buildConfigField("String", "NEWS_API", "\"${properties.getProperty("NEWS_API_TOKEN")}\"")
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
        buildConfig = true
    }
}

dependencies {

    //Splash Screen
    api(libs.androidx.core.splashscreen)

    //Datastore
    api (libs.androidx.datastore.preferences)

    //Dagger Hilt
    implementation(libs.hilt.android)
    kapt (libs.hilt.compiler)
    implementation (libs.androidx.hilt.navigation.compose)

    //Compose Navigation
    api (libs.androidx.navigation.compose)

    //Compose Foundation
    api (libs.androidx.foundation)

    //Compose for cotrolling System Controller (status bar)
    api (libs.accompanist.systemuicontroller)

    //Retrofit
    api (libs.retrofit)
    api (libs.converter.gson)

    //Paging 3
    api (libs.androidx.paging.runtime)
    api (libs.androidx.paging.compose)

    //Coil
    api(libs.coil.compose)

    //Room
    api (libs.androidx.room.runtime)
    kapt (libs.androidx.room.compiler)
    api (libs.androidx.room.ktx)

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
}