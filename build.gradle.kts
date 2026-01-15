import org.jetbrains.gradle.ext.runConfigurations
import org.jetbrains.gradle.ext.settings
import org.jetbrains.gradle.ext.Application

plugins {
    id("idea")
    id("java")
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.3"
    id("com.diffplug.spotless") version "8.1.0"
    id("com.gradleup.shadow") version "9.3.0"
}

group = providers.gradleProperty("group").get()
version = providers.gradleProperty("plugin_version").get()

val javaVersion = providers.gradleProperty("java_version");
val gameBuild = providers.gradleProperty("game_build")
val serverDir = file("$projectDir/server")
val serverJar = file(serverDir.resolve("HytaleServer.jar"))
val gamePath = System.getProperty("user.home") + "\\AppData\\Roaming\\Hytale"
val hytaleHome = "$gamePath\\install"
val patchLine = providers.gradleProperty("patch_line")
val assetsPath = "${hytaleHome}/${patchLine.get()}/package/game/latest/Assets.zip"

configurations {
    create("inShadow")
    implementation.get().extendsFrom(getByName("inShadow"))
}

spotless {
    java {
        endWithNewline()
        importOrder("", "java", group.toString(), "\\#")
        leadingTabsToSpaces(4)
        removeUnusedImports()
        trimTrailingWhitespace()
    }
}


java {
    toolchain.languageVersion = JavaLanguageVersion.of(javaVersion.get())
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(files(serverJar))
    runtimeOnly(files(serverJar))

    // Lib's
    add("inShadow", libs.hikari.cp)
    add("inShadow", libs.h2)

    // Driver
    add("inShadow", libs.sqlite.jdbc)
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        configurations = listOf(project.configurations.getByName("inShadow"))

        mergeServiceFiles()
        minimize {
            exclude(dependency("org.xerial:sqlite-jdbc"))
        }

        manifest {
            attributes(
                "Main-Class" to "ru.shikaru.mmo.HytaleMMO"
            )
        }
    }

    jar {
        enabled = false
    }

    assemble {
        dependsOn(spotlessApply, shadowJar)
    }

    register<JavaExec>("runServer") {
        dependsOn("assemble")

        workingDir = serverDir
        classpath = files(serverJar)

        args(
            "--allow-op",
            "--disable-sentry",
            "--assets=$assetsPath",
            "--mods=${file("build/libs").absolutePath}",
            "--auth-mode=authenticated"
        )
    }
}