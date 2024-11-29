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

package org.eclipse.dataspacetck.gradle.plugins.tckbuild;


import com.bmuschko.gradle.docker.DockerRemoteApiPlugin;
import io.github.gradlenexus.publishplugin.NexusPublishPlugin;
import org.eclipse.dataspacetck.gradle.conventions.DockerConvention;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Gradle plugin that customized the build for TCK projects
 */
public class TckBuildPlugin implements org.gradle.api.Plugin<Project> {


    @Override
    public void apply(@NotNull Project target) {
        var ext = target.getExtensions().create("dockerExtension", DockerExtension.class);

        target.getPlugins().apply(DockerRemoteApiPlugin.class);
        if (target.getRootProject() == target) {
            target.getPlugins().apply(NexusPublishPlugin.class);
        }

        target.afterEvaluate(project -> {
            new DockerConvention(ext).apply(project);
        });

    }

}
