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

import org.gradle.api.Project;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.plugins.signing.SigningExtension;
import org.gradle.plugins.signing.SigningPlugin;

public class SigningConvention {
    public void apply(Project project) {
        if (project.hasProperty("skip.signing")) {
            return;
        }
        var pubExt = project.getExtensions().getByType(PublishingExtension.class);
        var publications = pubExt.getPublications();

        project.getPlugins().apply(SigningPlugin.class);

        var signExt = project.getExtensions().getByType(SigningExtension.class);
        signExt.useGpgCmd();
        signExt.sign(publications);
    }
}
