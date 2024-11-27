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
            tags = listOf("tags", "dataspace", "dsp", "dcp", "tck", "plugins", "build")
            implementationClass = "org.eclipse.dataspacetck.gradle.plugins.tckbuild.TckBuildPlugin"
        }
        create("tckGen") {
            id = "org.eclipse.dataspacetck.tck-generator"
            displayName = "TCK Test Plan Generator"
            description = "Gradle Plugin to generate a test plan document in Markdown format"
            tags = listOf("tags", "dataspace", "dsp", "dcp", "tck", "plugins", "test", "testplan", "markdown")
            implementationClass = "org.eclipse.dataspacetck.gradle.plugins.tckgen.TckGeneratorPlugin"
        }
    }
}
