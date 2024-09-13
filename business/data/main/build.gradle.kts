import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.dagger.hilt)
}

val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.app.war.business.data.main"
    compileSdk = rootProject.ext.get("compileSdkVersion") as Int

    defaultConfig {
        buildConfigField("String", "API_BASE_URL", properties.getProperty("api_base_url"))
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }

    kotlinOptions {
        jvmTarget = rootProject.ext.get("jvmTargetVersion") as String
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(project(":common:domain:api"))
    implementation(project(":common:general:extensions"))
    implementation(project(":common:network"))
    implementation(project(":business:data:entity"))
    implementation(project(":business:domain:main"))
    implementation(project(":business:domain:model"))

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)

    implementation(libs.hilt.android)
    implementation(libs.androidx.junit.ktx)
    ksp(libs.hilt.android.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)
}
