import org.jetbrains.gradle.ext.runConfigurations
import org.jetbrains.gradle.ext.settings
import org.jetbrains.gradle.ext.Application

plugins {
    id("java")
    id("org.jetbrains.gradle.plugin.idea-ext").version("1.3")
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

java {
    toolchain.languageVersion = JavaLanguageVersion.of(javaVersion.get())
    withSourcesJar()
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(files(serverJar))
}

idea.project.settings {
    runConfigurations {
        create<Application>("HytaleServer") {
            mainClass = "com.hypixel.hytale.Main"
            moduleName = "${project.idea.module.name}.main"

            programParameters =
                "--allow-op " +
                "--disable-sentry " +
                "--assets=${assetsPath} " +
                "--mods=${file("src/main").absolutePath} " +
                "--auth-mode=authenticated"

            workingDirectory = serverDir.absolutePath
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

