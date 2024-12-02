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

import static org.eclipse.dataspacetck.gradle.Repositories.SNAPSHOT_REPO_URL;
import static org.eclipse.dataspacetck.gradle.Repositories.mavenRepo;

/**
 * Configures the root buildscript, i.e. adds repos
 */
public class RootBuildScriptConvention  {
    public void apply(Project target) {
        if (target == target.getRootProject()) {
            // configure buildscript repos
            target.getBuildscript().getRepositories().mavenLocal();
            target.getBuildscript().getRepositories().mavenCentral();
            target.getBuildscript().getRepositories().gradlePluginPortal();
            target.getBuildscript().getRepositories().maven(mavenRepo(SNAPSHOT_REPO_URL));
        }
    }
}
