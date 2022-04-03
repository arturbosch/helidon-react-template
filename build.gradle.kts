tasks.wrapper {
    version = libs.versions.gradle.get()
    distributionType = Wrapper.DistributionType.ALL
}

val buildFrontend = tasks.register("buildFrontend") {
    doLast {
        exec {
            setWorkingDir(rootProject.relativePath("frontend"))
            executable("yarn")
            args("build")
        }.assertNormalExitValue().rethrowFailure()
    }
}

val copyFrontend = tasks.register<Copy>("copyFrontend") {
    dependsOn(buildFrontend)
    from(rootProject.relativePath("frontend/dist"))
    into(rootProject.relativePath("backend/src/main/resources/static"))
}
