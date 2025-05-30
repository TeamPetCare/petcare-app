plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.petcare_app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.petcare_app"
        minSdk = 24
        targetSdk = 34
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
}


dependencies {
    implementation("io.insert-koin:koin-androidx-compose:3.5.3")
    implementation("io.insert-koin:koin-android:3.5.3")
    // Google Sign In SDK
    implementation("com.google.android.gms:play-services-auth:20.5.0")

    // Firebase SDK
    implementation(platform("com.google.firebase:firebase-bom:33.14.0"))
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")

    // Firebase UI Library
    implementation("com.firebaseui:firebase-ui-auth:8.0.2")
    implementation("com.firebaseui:firebase-ui-database:8.0.2")
    implementation("com.google.firebase:firebase-messaging-ktx")


    // Dependência do Firebase In-App Messaging
    implementation("com.google.firebase:firebase-inappmessaging-display-ktx")

    // Dependência do Google Analytics (essencial para o FIAM)
    implementation(libs.firebase.analytics.ktx)

    // Dependencia do ID de Instalação
    implementation (libs.firebase.installations.ktx)


    implementation(libs.koin.compose.viewmodel)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.retrofit)
    implementation(libs.coil.compose)
    implementation(libs.io.insert.koin.koin.android)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.material.icons.core)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.coil3.coil.compose)
    implementation(libs.ucrop)
    implementation(libs.androidx.foundation)
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation(libs.gson)
    implementation(libs.androidx.compose.ui.text.google.fonts)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.viewmodel.android)
    implementation(libs.androidx.espresso.core)
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.appcompat)
}

//apply(plugin = "com.google.gms.google-services")
