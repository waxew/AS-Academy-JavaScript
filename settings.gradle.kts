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

// ماژول‌های مرکزی بدون کپی سورس از submodule Core مصرف می‌شوند.
include(":academy-core-course")
project(":academy-core-course").projectDir = file("academy-core/course")
include(":academy-core-runtime")
project(":academy-core-runtime").projectDir = file("academy-core/core")
