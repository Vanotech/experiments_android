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

include(":data:lunardates")
include(":data:tvguide")

include(":feature:camera")
include(":feature:lunardates")
include(":feature:osinfo")
include(":feature:tvguide")
