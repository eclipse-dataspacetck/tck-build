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

repositories {
    gradlePluginPortal() // needed because some plugins are only published to the Plugin Portal
}

dependencies {
    implementation(gradleApi())
    implementation(libs.plugin.docker.remoteapi)
}

gradlePlugin {
    website = "https://projects.eclipse.org/projects/technology.dataspacetck"
    vcsUrl = "https://github.com/eclipse-dataspacetck"

    plugins {
        create("tckBuild") {
            id = "org.eclipse.dataspacetck.tck-build"
            displayName = "TCK Build Plugin"
            description = "Gradle Plugin to customize the TCK build"
            tags = listOf("tags", "dataspace", "dsp", "dcp", "tck", "plugins", "build")
            implementationClass = "org.eclipse.dataspacetck.gradle.plugins.tckbuild.TckBuildPlugin"
        }
    }
}
