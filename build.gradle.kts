import org.eclipse.dataspacetck.gradle.tckbuild.extensions.TckBuildExtension

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
    checkstyle
    `maven-publish`
    signing
    `java-library`
    `version-catalog`
    alias(libs.plugins.gradle.publish) apply false
}


// needed for running the dash tool
tasks.register("allDependencies", DependencyReportTask::class)


allprojects {
    apply(plugin = "org.eclipse.dataspacetck.tck-build")

    // for all gradle plugins:
    pluginManager.withPlugin("java-gradle-plugin") {
        apply(plugin = "com.gradle.plugin-publish")
    }

    // configure POM
    configure<TckBuildExtension> {
        pom {
            scmConnection = "https://github.com/eclipse-dataspacetck/dsp-tck.git"
            scmUrl = "scm:git:git@github.com:eclipse-dataspacetck/dsp-tck.git"
            groupId = "org.eclipse.dataspacetck.build"
            projectName = project.name
            description = "DSP Technology Compatibility Kit"
            projectUrl = "https://projects.eclipse.org/projects/technology.dataspacetck"
        }
    }

    // FIXME - workaround for https://github.com/gradle/gradle/issues/26091
    val signingTasks = tasks.withType<Sign>()
    tasks.withType<AbstractPublishToMaven>().configureEach {
        mustRunAfter(signingTasks)
    }
}