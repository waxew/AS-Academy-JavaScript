plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.asdevelopers.academy.javascript"
    compileSdk = 37

    defaultConfig {
        // applicationId ثابت می‌ماند تا نسخه Release روی نصب‌های قبلی قابل Update باشد.
        applicationId = "com.asdevelopers.academy.javascript"
        minSdk = 23
        targetSdk = 37
        versionCode = 3
        versionName = "1.0.0"
    }

    buildFeatures { compose = true }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // Signing فقط وقتی credentialهای Release توسط CI یا محیط محلی تزریق شده باشند ساخته می‌شود.
    // هیچ کلید یا passwordای داخل repository ذخیره نمی‌شود.
    val releaseStorePath = System.getenv("AS_ACADEMY_RELEASE_STORE_FILE")
    val releaseStorePassword = System.getenv("AS_ACADEMY_RELEASE_STORE_PASSWORD")
    val releaseKeyAlias = System.getenv("AS_ACADEMY_RELEASE_KEY_ALIAS")
    val releaseKeyPassword = System.getenv("AS_ACADEMY_RELEASE_KEY_PASSWORD")

    if (!releaseStorePath.isNullOrBlank() &&
        !releaseStorePassword.isNullOrBlank() &&
        !releaseKeyAlias.isNullOrBlank() &&
        !releaseKeyPassword.isNullOrBlank()
    ) {
        signingConfigs {
            create("release") {
                storeFile = file(releaseStorePath)
                storePassword = releaseStorePassword
                keyAlias = releaseKeyAlias
                keyPassword = releaseKeyPassword
                enableV1Signing = true
                enableV2Signing = true
                enableV3Signing = true
                enableV4Signing = true
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfigs.findByName("release")?.let { signingConfig = it }
        }
    }

    sourceSets.getByName("main").assets.srcDir("src/main/assets")
}

// MainCourse تنها منبع محتوای آموزشی JavaScript است.
// اگر submodule مقداردهی نشده باشد یا Course Package ناقص باشد، Build عمداً متوقف می‌شود.
val mainCourseJavaScript = rootProject.file("academy-main-course/courses/javascript/course")
require(mainCourseJavaScript.resolve("manifest.json").isFile) {
    "MainCourse JavaScript package is missing. Run: git submodule update --init --recursive"
}

val syncJavaScriptCourseAssets by tasks.registering(Copy::class) {
    from(mainCourseJavaScript)
    into(layout.projectDirectory.dir("src/main/assets/course/javascript"))
}

tasks.configureEach {
    val readsCourseAssets =
        (name.startsWith("merge") && name.endsWith("Assets")) ||
        name.contains("Lint", ignoreCase = true)
    if (readsCourseAssets) {
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
