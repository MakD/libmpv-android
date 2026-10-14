import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.SourcesJar

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "dev.jdtech.mpv"
    compileSdk = 36
    buildToolsVersion = "37.0.0"
    ndkVersion = "30.0.16248370"

    defaultConfig {
        minSdk = 26
        consumerProguardFiles("proguard-rules.pro")
        externalNativeBuild {
            cmake {
                arguments += listOf(
                    "-DANDROID_STL=c++_shared",
                )
                cFlags += "-Werror"
                cppFlags += "-std=c++11"
            }
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "4.1.2"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)
    signAllPublications()

    configure(
        platform = AndroidSingleVariantLibrary(
            javadocJar = JavadocJar.Empty(),
            sourcesJar = SourcesJar.Empty(),
            variant = "release",
        )
    )

    coordinates(
        groupId = "io.github.makd",
        artifactId = "libmpv",
        version = "1.1.0"
    )

    pom {
        name = "libmpv-android"
        description = "libmpv for Android with Vulkan"
        inceptionYear = "2023"
        url = "https://github.com/MakD/libmpv-android"
        licenses {
            license {
                name = "MIT license"
                url = "https://github.com/MakD/libmpv-android/blob/main/LICENSE"
            }
        }
        developers {
            developer {
                id = "jarnedemeulemeester"
                name = "Jarne Demeulemeester"
                email = "jarnedemeulemeester@gmail.com"
            }
            developer {
                id = "MakD"
                name = "MORPHi"
                email = "MakD@users.noreply.github.com"
            }
        }
        scm {
            url = "https://github.com/MakD/libmpv-android.git"
            connection = "scm:git@github.com:MakD/libmpv-android.git"
            developerConnection = "scm:git@github.com:MakD/libmpv-android.git"
        }
        issueManagement {
            system = "GitHub"
            url = "https://github.com/MakD/libmpv-android/issues"
        }
    }
}
