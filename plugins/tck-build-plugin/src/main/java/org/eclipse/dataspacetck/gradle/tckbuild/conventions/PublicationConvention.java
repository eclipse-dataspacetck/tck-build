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

import org.eclipse.dataspacetck.gradle.tckbuild.extensions.TckBuildExtension;
import org.gradle.api.Project;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;

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
        // do not publish the root project or modules without a build.gradle.kts
        if (target.getRootProject() == target || !target.file("build.gradle.kts").exists()) {
            return;
        }

        var buildExt = target.getExtensions().getByType(TckBuildExtension.class);
        var shouldPublish = buildExt.getPublish().getOrElse(DEFAULT_SHOULD_PUBLISH);

        if (shouldPublish) {
            var pe = target.getExtensions().getByType(PublishingExtension.class);

            if (pe.getPublications().findByName(target.getName()) == null) {
                pe.publications(publications -> publications.create(target.getName(), MavenPublication.class,
                        mavenPublication -> {
                            mavenPublication.from(target.getComponents().getByName("java"));
                            mavenPublication.setGroupId(buildExt.getPom().getGroupId());
                            mavenPublication.suppressPomMetadataWarningsFor("testFixturesApiElements");
                            mavenPublication.suppressPomMetadataWarningsFor("testFixturesRuntimeElements");
                        }));
            }
        }
    }
}
