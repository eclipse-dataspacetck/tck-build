/*
 *  Copyright (c) 2025 Metaform Systems Inc.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Metaform Systems Inc. - initial API and implementation
 *
 */

package org.eclipse.dataspacetck.gradle.tckbuild.conventions;

import org.eclipse.dataspacetck.gradle.Repositories;
import org.gradle.api.Project;

import java.net.URI;

public class RepositoriesConvention {
    public void apply(Project target) {
        var handler = target.getRepositories();
        handler.maven(r -> r.setUrl(URI.create(Repositories.SNAPSHOT_REPO_URL)));
        handler.mavenLocal();
        handler.mavenCentral();
    }
}
