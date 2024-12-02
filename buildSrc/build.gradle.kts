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
}

group = "org.eclipse.dataspacetck.build"

repositories {
    mavenCentral()
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
    }
    gradlePluginPortal()
    mavenLocal()
}

dependencies {

    implementation(libs.plugin.nexus.publish)
    implementation(libs.plugin.docker.remoteapi)
    implementation(libs.plugin.nexus.publish)

    api(libs.tck.common.api)
}

gradlePlugin {
    plugins {
        create("tckBuild") {
            id = "org.eclipse.dataspacetck.tck-build"
            group = "org.eclipse.dataspacetck.build"
            implementationClass = "org.eclipse.dataspacetck.gradle.tckbuild.plugins.TckBuildPlugin"
        }
    }
}

val generatedSourcesFolder = layout.buildDirectory.asFile.get().resolve("generated").resolve("sources")

sourceSets {
    main {
        java {
            srcDirs(
                generatedSourcesFolder,
                "../plugins/tck-build-plugin/src/main"
            )
        }
    }
}
