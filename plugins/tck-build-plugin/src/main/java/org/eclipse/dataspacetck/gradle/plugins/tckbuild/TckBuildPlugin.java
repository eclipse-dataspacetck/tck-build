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
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage;
import org.gradle.api.Project;
import org.gradle.api.tasks.Copy;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Gradle plugin that customized the build for TCK projects
 */
public class TckBuildPlugin implements org.gradle.api.Plugin<Project> {


    @Override
    public void apply(@NotNull Project target) {

        target.getPlugins().apply(DockerRemoteApiPlugin.class);

        var extension = target.getExtensions().create("dockerExtension", DockerExtension.class);

        target.afterEvaluate(project -> {
            var dockerFilePathDefault = Path.of(project.getProjectDir().getAbsolutePath(), DockerExtension.DEFAULT_DOCKERFILE_SUBPATH).toString();
            var dockerfile = extension.getDockerfilePath().convention(dockerFilePathDefault).get();

            if (Files.exists(Path.of(dockerfile))) {
                var copyLegalDocs = createCopyTask(project);
                var dockerTask = createDockerizeTask(project, dockerfile, extension);
                dockerTask.dependsOn(copyLegalDocs);
            }
        });

    }

    private static DockerBuildImage createDockerizeTask(Project project, String dockerfile, DockerExtension extension) {
        return project.getTasks().create("dockerize", DockerBuildImage.class, dbi -> {

            dbi.setGroup("build");
            dbi.setDescription("Task to build a runnable Docker image from a JAR file and a Dockerfile");
            dbi.getDockerFile().set(new File(dockerfile));

            //set image tags
            dbi.getImages().addAll(
                    project.getName() + ":" + project.getVersion(),
                    project.getName() + ":latest"
            );
            extension.getImageTags().forEach(tag -> dbi.getImages().add(project.getName() + ":" + tag));

            // set platform
            if (extension.getPlatform().isPresent()) {
                dbi.getPlatform().set(extension.getPlatform().get());
            }

            // set build args
            dbi.getBuildArgs().put("JAR", extension.getJarFilePath().convention("libs/" + project.getName() + ".jar").get());
            dbi.getBuildArgs().put("ADDITIONAL_FILES", extension.getAdditionalFiles());
            dbi.getInputDir().set(project.getProjectDir());
        });
    }

    private static @NotNull Copy createCopyTask(Project project) {
        return project.getTasks().create("copyLegalDocs", Copy.class, copy -> {
            copy.into(project.getLayout().getBuildDirectory().getAsFile().get() + "/legal")
                    .from(
                            project.getRootProject().getProjectDir().getPath() + "/SECURITY.md",
                            project.getRootProject().getProjectDir().getPath() + "/NOTICE.md",
                            project.getRootProject().getProjectDir().getPath() + "/DEPENDENCIES",
                            project.getRootProject().getProjectDir().getPath() + "/LICENSE",
                            project.getProjectDir().getPath() + "/notice.md"
                    );
        });
    }
}
