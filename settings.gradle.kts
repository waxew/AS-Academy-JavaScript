pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("academy-core/gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "AS-Academy-JavaScript"
include(":app")
include(":academy-course")

// نام‌های ماژول عمداً همان نام داخلی Core هستند تا dependencyهای Core بدون تغییر کار کنند.
include(":course")
project(":course").projectDir = file("academy-core/course")
include(":core")
project(":core").projectDir = file("academy-core/core")
