/*
 *  Copyright (c) 2022 Microsoft Corporation
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Microsoft Corporation - initial API and implementation
 *
 */

package org.eclipse.dataspacetck.gradle.tckbuild.conventions;

import org.eclipse.dataspacetck.gradle.tckbuild.extensions.TckBuildExtension;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.jvm.toolchain.JavaLanguageVersion;

/**
 * Enforces the Java version, generates Javadoc jar and Sources jar for publications
 */
public class JavaConvention {

    private static final int DEFAULT_JAVA_VERSION = 17;

    public void apply(Project target) {
        var javaPluginExt = target.getExtensions().getByType(JavaPluginExtension.class);
        var buildExt = target.getExtensions().getByType(TckBuildExtension.class);
        var javaVersion = buildExt.getJavaLanguageVersion()
                .getOrElse(JavaLanguageVersion.of(DEFAULT_JAVA_VERSION));

        // set java version
        javaPluginExt.toolchain(tc -> tc.getLanguageVersion().set(javaVersion));

        // making sure the code does not use any APIs from a more recent version.
        // Ref: https://docs.gradle.org/current/userguide/building_java_projects.html#sec:java_cross_compilation
        target.getTasks().withType(JavaCompile.class, compileTask -> {
            var options = compileTask.getOptions();
            options.getRelease().set(javaVersion.asInt());
            options.setFork(true);
            options.setIncremental(true);
        });


        // needed for publishing to maven central
        javaPluginExt.withJavadocJar();
        javaPluginExt.withSourcesJar();
    }

}
