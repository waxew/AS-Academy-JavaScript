plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.asdevelopers.academy.javascript.course"
    compileSdk = 37
    defaultConfig { minSdk = 23 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    api(project(":academy-core-course"))
    api(project(":academy-core-runtime"))
}
