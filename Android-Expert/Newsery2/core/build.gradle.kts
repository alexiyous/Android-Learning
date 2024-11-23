import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.alexius.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")


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

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    //Splash Screen
    api(libs.androidx.core.splashscreen)

    //Datastore
    api (libs.androidx.datastore.preferences)

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

    api(libs.koin.android)
    api("io.insert-koin:koin-android:3.4.0")
    api("io.insert-koin:koin-androidx-compose:3.4.0")

}