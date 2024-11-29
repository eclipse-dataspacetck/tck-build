/*
 *  Copyright (c) 2024 Bayerische Motoren Werke Aktiengesellschaft (BMW AG)
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Bayerische Motoren Werke Aktiengesellschaft (BMW AG) - initial API and implementation
 *
 */

plugins {
    `java-gradle-plugin`
    alias(libs.plugins.gradle.publish)
}

dependencies {
    implementation(gradleApi())
    implementation(libs.tck.common.testplan.gen)
}

gradlePlugin {
    website = "https://projects.eclipse.org/projects/technology.dataspacetck"
    vcsUrl = "https://github.com/eclipse-dataspacetck"

    plugins {
        create("tckGen") {
            id = "org.eclipse.dataspacetck.tck-generator"
            displayName = "TCK Test Plan Generator Plugin"
            description = "Gradle Plugin to generate a test plan document in Markdown format"
            tags = listOf("tags", "dataspace", "dsp", "dcp", "tck", "plugins", "test", "testplan", "markdown")
            implementationClass = "org.eclipse.dataspacetck.gradle.plugins.tckgen.TckGeneratorPlugin"
        }
    }
}
