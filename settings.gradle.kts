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
}

rootProject.name = "NTAlarmTestApp"
include(":app")
include(":common")
include(":database")
include(":data:location")
include(":data:photos")
include(":data:photodetails")
include(":feature:photos")
