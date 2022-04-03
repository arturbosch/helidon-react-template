tasks.wrapper {
    version = libs.versions.gradle.get()
    distributionType = Wrapper.DistributionType.ALL
}

val frontendInputs = rootProject.relativePath("frontend/dist")
val frontendOutputs = rootProject.relativePath("backend/src/main/resources/static")

val buildFrontend = tasks.register("buildFrontend") {
    outputs.dir(frontendInputs)
    doLast {
        exec {
            setWorkingDir(rootProject.relativePath("frontend"))
            executable("yarn")
            args("build")
        }.assertNormalExitValue().rethrowFailure()
    }
}

val copyFrontend = tasks.register<Copy>("copyFrontend") {
    inputs.dir(frontendInputs)
    outputs.dir(frontendOutputs)
    dependsOn(buildFrontend)
    from(frontendInputs)
    into(frontendOutputs)
}
