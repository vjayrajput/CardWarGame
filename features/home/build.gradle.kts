plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.dagger.hilt)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.app.war.features.home"
    compileSdk = rootProject.ext.get("compileSdkVersion") as Int

    defaultConfig {
        minSdk = rootProject.ext.get("minSdkVersion") as Int
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }

    kotlinOptions {
        jvmTarget = rootProject.ext.get("jvmTargetVersion") as String
    }
}

dependencies {
    implementation(project(":common:navigation"))
    implementation(project(":common:ui:resources:drawables"))
    implementation(project(":common:ui:resources:strings"))

    implementation(libs.androidx.material3)

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)
}
