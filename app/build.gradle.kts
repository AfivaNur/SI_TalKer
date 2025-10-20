plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("org.jetbrains.kotlin.plugin.compose")
//    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"

    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"


}

android {
    namespace = "com.afiva.appskelurahan"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.afiva.appskelurahan"
        minSdk = 28
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
        kotlinCompilerExtensionVersion = "1.5.14"
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
    implementation(libs.androidx.room.common.jvm)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


//lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

//ktor
    implementation("io.ktor:ktor-client-core:3.0.0-rc-1")
    implementation("io.ktor:ktor-client-cio:3.0.0-rc-1")
    implementation("io.ktor:ktor-client-content-negotiation:3.0.0-rc-1")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0-rc-1")
    implementation("io.ktor:ktor-client-okhttp:3.0.0-rc-1")

//github
    implementation(platform("io.github.jan-tennert.supabase:bom:3.2.0-beta-2"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt:3.2.0-beta-2")
    implementation("io.github.jan-tennert.supabase:auth-kt:3.2.0-beta-2")
    implementation("io.github.jan-tennert.supabase:realtime-kt:3.2.0-beta-2")
    // Database access
    implementation("io.github.jan-tennert.supabase:storage-kt:3.2.0-beta-2")
    implementation("com.github.librepdf:openpdf:1.3.30")

//Navigation
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)

//DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

//Coil for Image Loading
    implementation("io.coil-kt:coil-compose:2.7.0")

    implementation("com.google.dagger:hilt-android:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    implementation("com.patrykandpatrick.vico:compose:1.13.0")
    implementation("com.patrykandpatrick.vico:core:1.13.0")

//    implementation ("com.google.maps.android:maps-compose:3.2.0")
//    implementation ("com.google.android.gms:play-services-maps:18.2.0")
}