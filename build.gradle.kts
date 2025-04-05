import org.apache.tools.ant.filters.ReplaceTokens
import java.security.MessageDigest

plugins {
    id("java")
    id("application")
    id("com.gradleup.shadow") version "8.3.3"
}

group = "pl.erpix.tiruka"
version = "1.0.0"

application {
    mainClass = "dev.erpix.tiruka.TirukaBootstrap"
}

data class Dependency(val groupId: String, val artifactId: String, val version: String)
val dynamicDependencies = mapOf(
    "h2"            to Dependency("com.h2database", "h2", "2.3.232"),
    "mysql"         to Dependency("com.mysql", "mysql-connector-j", "9.2.0"),
    "mariadb"       to Dependency("org.mariadb.jdbc", "mariadb-java-client", "3.4.1"),
    "postgresql"    to Dependency("org.postgresql", "postgresql", "42.7.4"),
    "sqlite"        to Dependency("org.xerial", "sqlite-jdbc", "3.46.1.3"),
    "jedis"         to Dependency("redis.clients", "jedis", "5.2.0"),
    "commons-pool2" to Dependency("org.apache.commons", "commons-pool2", "2.12.0") // Required by Jedis
)

// Actual main class started from the bootstrap
val actualMainClass = "dev.erpix.tiruka.Tiruka"
val githubUrl = "https://github.com/erpixxx/Tiruka"
val website = "https://tiruka.erpix.pl"

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.5.6")
    implementation("com.google.guava:guava:33.3.1-jre")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("net.dv8tion:JDA:5.2.1")
    implementation("net.sf.jopt-simple:jopt-simple:4.7")
    implementation("io.prometheus:simpleclient:0.16.0")
    implementation("io.prometheus:simpleclient_hotspot:0.16.0")
    implementation("io.prometheus:simpleclient_httpserver:0.16.0")
    implementation("org.jline:jline:3.27.0")
    implementation("org.jetbrains:annotations:24.0.0")
    implementation("org.yaml:snakeyaml:2.2")

    dynamicDependencies.forEach { dep ->
        compileOnly("${dep.value.groupId}:${dep.value.artifactId}:${dep.value.version}")
    }

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.register<Copy>("sourcesForRelease") {
    from("src/main/java") {
        include("**/TirukaConstants.java")
        filter<ReplaceTokens>("tokens" to buildMap {
            put("main-class", actualMainClass)
            put("github-url", githubUrl)
            put("version", project.version.toString())
            put("website", website)
            dynamicDependencies.forEach { (k, v) ->
                put("${k}-artifact-id", v.artifactId)
                put("${k}-group-id", v.groupId)
                put("${k}-version", v.version)
            }
        })
    }
    into("build/filteredSrc")
    includeEmptyDirs = false
    outputs.upToDateWhen { false }
}

tasks.register<SourceTask>("generateJavaSources") {
    source = fileTree("build/filteredSrc") + sourceSets["main"].allJava.matching {
        exclude("**/TirukaConstants.java")
    }

    dependsOn("sourcesForRelease")
}

tasks.named<JavaCompile>("compileJava") {
    source = tasks.named<SourceTask>("generateJavaSources").get().source

    dependsOn("generateJavaSources")
}

tasks.register("generateChecksum") {
    dependsOn(tasks.shadowJar)
    doLast {
        val jarFile = tasks.shadowJar.get().archiveFile.get().asFile
        val checksumFile = File(jarFile.parent, "${jarFile.name}.sha256")

        fun calculateChecksum(file: File): String {
            val digest = MessageDigest.getInstance("SHA-256")
            file.inputStream().use { fis ->
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (fis.read(buffer).also { bytesRead = it } != -1) {
                    digest.update(buffer, 0, bytesRead)
                }
            }
            return digest.digest().joinToString("") { "%02x".format(it) }
        }

        val checksum = calculateChecksum(jarFile)
        checksumFile.writeText("$checksum *${jarFile.name}\n")
    }
}

tasks.jar {
    enabled = false
}

tasks.shadowJar {
    archiveClassifier.set("")
}

tasks.distTar {
    dependsOn(tasks.shadowJar)
}

tasks.startScripts {
    dependsOn(tasks.shadowJar)
}

tasks.build {
    dependsOn(tasks.shadowJar)
    dependsOn("generateChecksum")
}

tasks.test {
    useJUnitPlatform()
}