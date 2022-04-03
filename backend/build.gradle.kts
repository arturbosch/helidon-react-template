plugins {
    id("java")
    id("application")
    kotlin("jvm") version libs.versions.kotlin.get()
}

tasks.withType<JavaCompile> {
    sourceCompatibility = libs.versions.jvm.get()
    targetCompatibility = libs.versions.jvm.get()
    options.encoding = Charsets.UTF_8.toString()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = libs.versions.jvm.get()
    }
}

tasks.test {
    useJUnitPlatform()
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    // helidon
    implementation(enforcedPlatform("io.helidon:helidon-dependencies:${libs.versions.helidon.get()}"))
    implementation("io.helidon.webserver:helidon-webserver")
    implementation("io.helidon.webserver:helidon-webserver-static-content")
    implementation("io.helidon.media:helidon-media-jsonp")
    implementation("io.helidon.config:helidon-config-yaml")
    implementation("io.helidon.health:helidon-health")
    implementation("io.helidon.health:helidon-health-checks")
    implementation("io.helidon.metrics:helidon-metrics")
    // kotlin
    implementation(libs.kotlin)

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("io.helidon.webclient:helidon-webclient")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

// copy runtime dependencies into libs folder so backend.jar with manifest entry defined later is executable
val copyLibs = tasks.register("copyLibs", Copy::class.java) {
    dependsOn(tasks.jar)
    from(configurations.runtimeClasspath)
    into("build/libs/libs")
}

fun copyFrontendTask() = rootProject.tasks.getByName("copyFrontend")

tasks.assemble {
    dependsOn(copyLibs)
    dependsOn(copyFrontendTask())
}

tasks.processResources {
    dependsOn(copyFrontendTask())
}

val mainName = "template.Main"

version = libs.versions.template
group = "template"

// default jar configuration
// set the main classpath
// add each jar under build/libs/libs into the classpath
tasks.jar {
    archiveFileName.set("${project.name}.jar")
    manifest {
        attributes("Main-Class" to mainName,
            "Class-Path" to configurations.runtimeClasspath.get().files.joinToString(" ") { "libs/${it.name}" })
    }
}

application {
    mainClass.set(mainName)
}
