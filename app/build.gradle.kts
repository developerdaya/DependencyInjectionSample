plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.curve.delivery"
    compileSdk = 34
    viewBinding {
        enable = true
    }
    buildFeatures {
        compose =  true
    }

    defaultConfig {
        applicationId = "com.curve.delivery"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_URL", "\"https://www.curveapp.co/api/v1/\"")
        }

        debug {
            buildConfigField("String", "API_URL", "\"https://www.curveapp.co/api/v1/\"")
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
        buildConfig = true
        compose = true
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
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.compose.material)
    implementation(libs.volley)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //----------------------------CCP API------------------------//
    implementation("com.hbb20:ccp:2.5.2")
    implementation ("com.github.abi9567:simpleCountryPicker:1.0.2")

    //----------------------------Google Maps Lib------------------------//
    implementation("com.google.android.gms:play-services-maps:18.0.2")
    implementation("com.google.maps.android:maps-compose:2.5.0")

    //----------------------------Composed Lib------------------------//
    implementation("io.coil-kt:coil-compose:2.0.0-rc01")
    implementation ("com.google.accompanist:accompanist-pager:0.12.0")
    implementation ("androidx.compose.foundation:foundation:1.0.0")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
    implementation ("com.github.skydoves:landscapist-glide:1.5.0")
    implementation ("com.google.accompanist:accompanist-permissions:0.35.0-alpha")

    //----------------------------Util Lib------------------------//
    implementation ("com.github.abi9567:simpleCountryPicker:1.0.2")
    implementation ("com.yogeshpaliyal:speld:1.0.0")
    implementation("com.github.composeuisuite:ohteepee:1.0.9")

        implementation ("com.github.mukeshsolanki.android-otpview-pinview:otpview-compose:3.1.0")


    //----------------------------MVVM API------------------------//
    implementation("androidx.compose.runtime:runtime-livedata:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    //----------------------------GIF:VIEW API------------------------//

    implementation ("io.coil-kt:coil-compose:2.0.0")
    implementation ("io.coil-kt:coil-gif:2.0.0")

    implementation ("com.github.bumptech.glide:glide:4.15.1")
    implementation("com.github.CanHub:Android-Image-Cropper:4.0.0")


    /***************************dagger with hilt******************/
     implementation(libs.hilt.android)
     kapt(libs.hilt.android.compiler)

    /*************Coroutines ********************/
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.lifecycle.lifecycle.viewmodel.ktx2)
    implementation(libs.androidx.lifecycle.runtime.ktx.v231)
}

kapt {
    correctErrorTypes = true
}