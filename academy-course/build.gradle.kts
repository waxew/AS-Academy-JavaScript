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
    // مدل‌ها و قراردادهای Course Package از هسته مشترک Academy دریافت می‌شوند.
    api(project(":course"))
    api(project(":core"))

    // Runner جاوااسکریپت برای تبدیل callback مربوط به WebView به API تعلیقی CodeRunner
    // از coroutine استفاده می‌کند. وابستگی به صورت مستقیم تعریف می‌شود تا ماژول Course
    // به جزئیات transitive ماژول Core وابسته نباشد.
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
}
