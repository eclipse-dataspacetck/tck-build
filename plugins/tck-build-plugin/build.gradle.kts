plugins {
    `java-gradle-plugin`
    alias(libs.plugins.gradle.publish)
}

dependencies {
    api(project(":annotation-processors:test-plan-generator"))
    implementation(gradleApi())
}

gradlePlugin {
    website = "https://projects.eclipse.org/projects/technology.dataspacetck"
    vcsUrl = "https://github.com/eclipse-dataspacetck"

    plugins {
        create("tckBuild") {
            id = "org.eclipse.dataspacetck.tck-build"
            displayName = "TCK Build"
            description = "Gradle Plugin to customize the TCK build"
            tags = listOf("tags", "dataspace", "dsp", "dcp", "tck", "plugins")
            implementationClass = "org.eclipse.dataspacetck.plugins.tckbuild.TckBuildPlugin"
        }
    }
}
