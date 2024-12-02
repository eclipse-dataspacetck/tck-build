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

import org.gradle.api.Project;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.jvm.tasks.Jar;

import java.nio.file.Path;

/**
 * Adds the LICENSE and NOTICE.md file to every JAR
 */
public class JarConvention {
    public void apply(Project target) {
        var task = target.getTasks().named("jar");
        if (task.isPresent()) {
            var jarTask = (Jar) task.get();
            var rootProjectPath = target.getRootProject().getProjectDir().getAbsolutePath();
            var licenseFile = Path.of(rootProjectPath, "LICENSE");
            var noticeFile = Path.of(rootProjectPath, "NOTICE.md");
            var dependenciesFile = Path.of(rootProjectPath, "DEPENDENCIES");
            jarTask.setDuplicatesStrategy(DuplicatesStrategy.INCLUDE);
            jarTask.metaInf(metaInf -> metaInf.from(licenseFile.toString(), noticeFile.toString(), dependenciesFile.toString()));
        }
    }
}
