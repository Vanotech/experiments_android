pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Experiments"
include(":app")

include(":core:ui")
include(":core:utils")

include(":data:camera")
include(":data:lunardates")
include(":data:media")
include(":data:tvguide")

include(":feature:camera")
include(":feature:lunardates")
include(":feature:media")
include(":feature:osinfo")
include(":feature:tvguide")
