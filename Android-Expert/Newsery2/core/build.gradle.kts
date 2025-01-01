import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.kotlin.dsl.test

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
        buildConfigField("String", "HUGGINGFACE_API", "\"${properties.getProperty("HUGGINGFACE_TOKEN")}\"")
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

    //SQL Cipher
    implementation(libs.android.database.sqlcipher)
    implementation(libs.androidx.sqlite.ktx)

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

    //OkHttp Logging Interceptor
    implementation(libs.logging.interceptor)

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
    api(libs.koin.androidx.compose)


    // Media3 for audio playback
    api("androidx.media3:media3-exoplayer:1.5.1")
    api("androidx.media3:media3-ui:1.5.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation (libs.mockk)
    testImplementation (libs.kotlinx.coroutines.test)
}