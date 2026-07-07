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

package org.eclipse.dataspacetck.gradle.tckbuild.conventions;

import com.vanniktech.maven.publish.DeploymentValidation;
import com.vanniktech.maven.publish.MavenPublishBaseExtension;
import com.vanniktech.maven.publish.MavenPublishPlugin;
import org.eclipse.dataspacetck.gradle.tckbuild.extensions.TckBuildExtension;
import org.gradle.api.Project;

public class PublicationConvention {
    /**
     * Default setting for publication of a project.
     */
    private static final boolean DEFAULT_SHOULD_PUBLISH = true;

    /**
     * Checks whether publishing is explicitly set to false for the target project and, if it is
     * not, adds a Maven publication to the project, if none exists. This only applies for
     * sub-projects that contain a build.gradle.kts file.
     *
     * @param target The project to which the convention applies
     */
    public void apply(Project target) {
        if (target.getRootProject() == target || !target.file("build.gradle.kts").exists()) {
            return;
        }

        var buildExt = target.getExtensions().getByType(TckBuildExtension.class);
        var shouldPublish = buildExt.getPublish().getOrElse(DEFAULT_SHOULD_PUBLISH);

        if (shouldPublish) {
            target.getPlugins().apply(MavenPublishPlugin.class);

            target.getExtensions().configure(MavenPublishBaseExtension.class, extension -> {
                var automaticRelease = true;
                extension.publishToMavenCentral(automaticRelease, DeploymentValidation.NONE);
                extension.signAllPublications();
            });
        }
    }
}
