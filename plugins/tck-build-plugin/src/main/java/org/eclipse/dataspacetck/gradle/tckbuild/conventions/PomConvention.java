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

import org.eclipse.dataspacetck.gradle.tckbuild.extensions.PomExtension;
import org.eclipse.dataspacetck.gradle.tckbuild.extensions.TckBuildExtension;
import org.gradle.api.Project;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPom;
import org.gradle.api.publish.maven.MavenPublication;

public class PomConvention {

    private static final String PROJECT_URL = "https://projects.eclipse.org/projects/technology.dataspacetck";

    public void apply(Project project) {
        project.afterEvaluate(p -> {
            var pomExtension = p.getExtensions().getByType(TckBuildExtension.class).getPom();
            var pubExtension = p.getExtensions().getByType(PublishingExtension.class);


            pubExtension.getPublications().stream()
                    .filter(pub -> pub instanceof MavenPublication)
                    .map(pub -> (MavenPublication) pub)
                    .forEach(mp -> mp.pom(mavenPom -> setPomInformation(project, pomExtension, mavenPom)));
        });
    }

    private void setPomInformation(Project project, PomExtension pomExtension, MavenPom pom) {
        // these properties are mandatory!
        var projectName = pomExtension.getProjectName().getOrElse(project.getName());
        var description = pomExtension.getDescription().getOrElse("Dataspace TCK :: " + project.getName());
        var projectUrl = pomExtension.getProjectUrl().getOrElse(PROJECT_URL);
        pom.getName().set(projectName);
        pom.getDescription().set(description);
        pom.getUrl().set(projectUrl);

        // we'll provide a sane default for these properties
        pom.licenses(l -> l.license(pl -> {
            pl.getName().set(pomExtension.getLicenseName().getOrElse("The Apache License, Version 2.0"));
            pl.getUrl().set(pomExtension.getLicenseUrl().getOrElse("http://www.apache.org/licenses/LICENSE-2.0.txt"));
        }));

        pomExtension.getDeveloper().forEach(developer -> {
            pom.developers(l -> l.developer(dev -> {
                dev.getName().set(developer.name());
                dev.getId().set(developer.id());
                dev.getEmail().set(developer.email());
            }));
        });

        pom.scm(scm -> {
            scm.getUrl().set(pomExtension.getScmUrl());
            scm.getConnection().set(pomExtension.getScmConnection());
        });
    }
}
