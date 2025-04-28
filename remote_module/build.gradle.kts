plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}
apply {
    from ("$rootDir/versions.gradle.kts")
}


android {
    namespace = "com.umirov.remote_module"
    compileSdk = 35
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField("boolean", "DEBUG", "true")
        }
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
}

dependencies {
    kapt(libs.dagger.compiler.v2511)

    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.core.ktx.v1101)
    implementation(libs.androidx.appcompat)
    implementation(libs.material.v1100)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.rxjava3.retrofit.adapter)
    implementation(libs.rxjava.v3010)
    implementation(libs.dagger)

    implementation(libs.androidx.core.ktx.v1101)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}