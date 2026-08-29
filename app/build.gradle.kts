plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.asdevelopers.academy.javascript"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.asdevelopers.academy.javascript"
        minSdk = 23
        targetSdk = 37
        versionCode = 2
        versionName = "0.2.0"
    }

    buildFeatures { compose = true }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // فقط Course Package آموزشی وارد APK می‌شود؛ ریشه پروژه و خروجی Gradle هرگز asset نیستند.
    sourceSets.getByName("main").assets.srcDir("src/main/assets")
}

// Course Package مستقل از کد اپ نگهداری می‌شود. قبل از Build فقط محتوای course/javascript
// به ساختار استاندارد assets/course/javascript کپی می‌شود تا Loader مشترک Core آن را بخواند.
val syncJavaScriptCourseAssets by tasks.registering(Copy::class) {
    from(rootProject.file("course/javascript"))
    into(layout.projectDirectory.dir("src/main/assets/course/javascript"))
}

tasks.configureEach {
    if (name.startsWith("merge") && name.endsWith("Assets")) {
        dependsOn(syncJavaScriptCourseAssets)
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":course"))
    implementation(project(":academy-course"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
}
