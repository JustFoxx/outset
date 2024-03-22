import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.intellij)
    alias(libs.plugins.lombok)
    alias(libs.plugins.fabricloom)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ktfmt)
}

repositories {
    maven("https://jitpack.io")
    maven("https://api.modrinth.com/maven")
    maven("https://maven.ladysnake.org/releases")
    maven("https://maven.cafeteria.dev")
    maven("https://maven.jamieswhiteshirt.com/libs-release")
    maven("https://masa.dy.fi/maven")
    maven("https://maven.shedaniel.me")
    maven("https://maven.terraformersmc.com")
    maven("https://maven.quiltmc.org/repository/release")
}

dependencies {
    minecraft(libs.bundles.minecraft)
    mappings(libs.bundles.yarn)
    modImplementation(libs.bundles.fabric)
    implementation(libs.bundles.vineflower)
    implementation(libs.bundles.night)
    include(libs.bundles.night)
    modImplementation(libs.bundles.kotlin)
}

val sourceCompatibility = JavaVersion.VERSION_17
val targetCompatibility = JavaVersion.VERSION_17
val archivesBaseName = "archivesBaseName".configKey

version = "${"mod_version".configKey}+${libs.versions.minecraft.get()}"

tasks {
    processResources {
        expand(mapOf(
            "version" to version,
            "mod_id" to "mod_id".configKey,
            "java_version" to "$sourceCompatibility",
            "loader_version" to libs.versions.fabricloader.get(),
            "minecraft_version" to libs.versions.minecraft.get(),
            "kotlin_fabric_version" to libs.versions.kotlin.fabric.get()
        ))
    }
    jar {
        from("LICENSE") {
            rename { "${it}_$archivesBaseName" }
        }
    }

    compileJava {
        options.release = 17
    }

    withType<KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = "17"
        kotlinOptions.freeCompilerArgs += listOf(
            "-Xlambdas=indy",
            "-Xassertions=jvm",
            "-Xbackend-threads=8",
            "-Xenhance-type-parameter-types-to-def-not-null",
            "-Xjvm-default=all",
            "-Xsam-conversions=indy",
            "-Xstring-concat=indy-with-constants",
            "-Xtype-enhancement-improvements-strict-mode",
            "-Xcontext-receivers",
            "-Xklib-enable-signature-clash-checks",
            "-Xextended-compiler-checks",
        )
    }
}



intellij {
    version = "2023.3.4"
    type = "IU"
}

val String.configKey: String
    get() = project.properties[this].toString()


loom {
    serverOnlyMinecraftJar()
}

ktfmt {
    kotlinLangStyle()
}

kotlin {
    compilerOptions.jvmTarget = JvmTarget.JVM_17
    compilerOptions.verbose = true
}