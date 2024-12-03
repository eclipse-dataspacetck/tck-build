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

package org.eclipse.dataspacetck.gradle.tckbuild.plugins;


import com.bmuschko.gradle.docker.DockerRemoteApiPlugin;
import io.github.gradlenexus.publishplugin.NexusPublishPlugin;
import org.eclipse.dataspacetck.gradle.tckbuild.conventions.DockerConvention;
import org.eclipse.dataspacetck.gradle.tckbuild.conventions.JarConvention;
import org.eclipse.dataspacetck.gradle.tckbuild.conventions.JavaConvention;
import org.eclipse.dataspacetck.gradle.tckbuild.conventions.NexusPublishingConvention;
import org.eclipse.dataspacetck.gradle.tckbuild.conventions.PomConvention;
import org.eclipse.dataspacetck.gradle.tckbuild.conventions.PublicationConvention;
import org.eclipse.dataspacetck.gradle.tckbuild.conventions.RootBuildScriptConvention;
import org.eclipse.dataspacetck.gradle.tckbuild.conventions.SigningConvention;
import org.eclipse.dataspacetck.gradle.tckbuild.extensions.DockerExtension;
import org.eclipse.dataspacetck.gradle.tckbuild.extensions.TckBuildExtension;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.quality.CheckstylePlugin;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Gradle plugin that customized the build for TCK projects
 */
public class TckBuildPlugin implements org.gradle.api.Plugin<Project> {


    @Override
    public void apply(@NotNull Project target) {
        var ext = target.getExtensions().create("dockerExtension", DockerExtension.class);
        target.getExtensions().create("tckBuild", TckBuildExtension.class, target.getObjects());

        target.getPlugins().apply(DockerRemoteApiPlugin.class);
        if (target.getRootProject() == target) {
            target.getPlugins().apply(NexusPublishPlugin.class);
        }

        target.getPlugins().apply(JavaLibraryPlugin.class);
        target.getPlugins().apply(CheckstylePlugin.class);
        target.getPlugins().apply(MavenPublishPlugin.class);
        target.getPlugins().apply(JavaPlugin.class);


        new NexusPublishingConvention().apply(target);

        target.afterEvaluate(project -> {
            new RootBuildScriptConvention().apply(project);
            new DockerConvention(ext).apply(project);
            new SigningConvention().apply(project);
            new PublicationConvention().apply(project);
            new PomConvention().apply(project);
            new JavaConvention().apply(project);
            new JarConvention().apply(project);
        });

    }

}
