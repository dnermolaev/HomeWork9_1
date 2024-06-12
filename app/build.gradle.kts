plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("org.jetbrains.kotlin.kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "ru.netology.nmedia"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.netology.nmedia"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            manifestPlaceholders["usesCleartextTraffic"] = false
            buildConfigField ("String", "BASE_URL", "\"https://netomedia.ru\"")
        }
        debug {
            manifestPlaceholders["usesCleartextTraffic"] = true
            buildConfigField ("String", "BASE_URL", "\"http://10.0.2.2:9999\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val core_version = "1.12.0"
    val appcompat_version = "1.6.1"
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    val mdc_version = "1.11.0"
    val constraintlayout_version = "2.1.4"
    val recyclerview_version = "1.3.2"
    val junit_version = "4.13.2"
    val ext_junit_version = "1.1.5"
    val espresso_core_version = "3.5.1"
    val activity_version = "1.8.2"
    val lifecycle_version = "2.7.0"
    val mds_version = "1.9.0"
    val fragment_version = "1.6.2"
    val gson_version = "2.10.1"
    val nav_version = "2.7.7"
    val room_version = "2.6.1"
    val play_services_base_version = "18.3.0"
    val glide_version = "4.16.0"
    val retrofit_version = "2.10.0"
    val retrofitgson_version = "2.10.0"
    val okhttplogging_version = "4.12.0"

    implementation ("androidx.core:core-ktx:$core_version")
    implementation ("androidx.appcompat:appcompat:$appcompat_version")
    implementation ("com.google.android.material:material:$mdc_version")
    implementation ("androidx.constraintlayout:constraintlayout:$constraintlayout_version")
    implementation ("androidx.recyclerview:recyclerview:$recyclerview_version")
    implementation ("androidx.activity:activity-ktx:$activity_version")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation ("com.google.android.material:material:$mdc_version")
    implementation("androidx.fragment:fragment-ktx:$fragment_version")
    implementation ("com.google.code.gson:gson:$gson_version")
    implementation ("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation ("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation ("androidx.room:room-runtime:$room_version")
    kapt ("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation ("com.google.firebase:firebase-messaging-ktx")
    implementation ("com.google.android.gms:play-services-base:$play_services_base_version")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation ("com.github.bumptech.glide:glide:$glide_version")
    implementation ("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitgson_version")
    implementation ("com.squareup.okhttp3:logging-interceptor:$okhttplogging_version")
    implementation ("com.github.bumptech.glide:glide:$glide_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}