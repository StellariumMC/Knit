import dev.deftu.gradle.utils.version.MinecraftVersions
import dev.deftu.gradle.utils.includeOrShade

plugins {
    java
    kotlin("jvm")
    id("dev.deftu.gradle.multiversion")
    id("dev.deftu.gradle.tools")
    id("dev.deftu.gradle.tools.resources")
    id("dev.deftu.gradle.tools.bloom")
    id("dev.deftu.gradle.tools.shadow")
    id("dev.deftu.gradle.tools.minecraft.loom")
    id("dev.deftu.gradle.tools.minecraft.releases")
    id("dev.deftu.gradle.tools.publishing.maven")
}

apply(rootProject.file("secrets.gradle.kts"))

toolkitMultiversion {
    moveBuildsToRootProject.set(true)
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    if (mcData.isFabric) {
        modImplementation("net.fabricmc.fabric-api:fabric-api:${mcData.dependencies.fabric.fabricApiVersion}")
        modImplementation("net.fabricmc:fabric-language-kotlin:${mcData.dependencies.fabric.fabricLanguageKotlinVersion}")
    }

    if (mcData.version <= MinecraftVersions.VERSION_1_12_2 && mcData.isForge) includeOrShade(kotlin("stdlib-jdk8"))

    modApi(includeOrShade("com.mojang:brigadier:1.2.9")!!)
}

toolkitMavenPublishing {
    artifactName.set("knit")
    setupRepositories.set(false)
}

java {
    withSourcesJar()
    withJavadocJar()
}

afterEvaluate {
    publishing {
        publications {
            named<MavenPublication>("mavenJava") {
                pom {
                    name.set("Knit")
                    description.set("A fork of OmniCore for StellariumMC's mods.")
                    url.set("https://github.com/StellariumMC/knit")
                    licenses {
                        license {
                            name.set("GNU General Public License v3.0")
                            url.set("https://www.gnu.org/licenses/gpl-3.0.en.html")
                        }
                    }
                    developers {
                        developer {
                            id.set("aurielyn")
                            name.set("Aurielyn")
                        }
                    }
                    scm {
                        url.set("https://github.com/StellariumMC/knit")
                    }
                }
            }
        }
        repositories {
            maven {
                name = "Bundle"
                url = uri(layout.buildDirectory.dir("central-bundle"))
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}

val createBundle = tasks.register<Zip>("createBundle") {
    from(layout.buildDirectory.dir("central-bundle"))
    archiveFileName.set("knit-$version")
    destinationDirectory.set(layout.buildDirectory)
    dependsOn("publishMavenJavaPublicationToBundleRepository")
}

tasks.register<Exec>("publishToSonatype") {
    group = "publishing"
    dependsOn(createBundle)
    onlyIf { mcData.version != MinecraftVersions.VERSION_1_16_5 }
    commandLine(
        "curl", "-X", "POST",
        "-u", "${findProperty("sonatype.username")}:${findProperty("sonatype.password")}",
        "-F", "bundle=@${layout.buildDirectory.file("knit-$version").get().asFile.absolutePath}",
        "https://central.sonatype.com/api/v1/publisher/upload?publishingType=AUTOMATIC"
    )
}