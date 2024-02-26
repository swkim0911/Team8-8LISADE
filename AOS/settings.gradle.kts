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
        maven { setUrl("https://jitpack.io") }
        mavenCentral()
        maven(url = "https://devrepo.kakao.com/nexus/repository/kakaomap-releases/")
    }
}

rootProject.name = "AOS"
include(":app")
