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

package org.eclipse.dataspacetck.gradle;

import io.github.gradlenexus.publishplugin.NexusRepository;
import io.github.gradlenexus.publishplugin.NexusRepositoryContainer;
import org.gradle.api.Action;

import java.net.URI;

public class Repositories {

    public static final String SNAPSHOT_REPO_URL = "https://oss.sonatype.org/content/repositories/snapshots/";
    public static final String NEXUS_REPO_URL = "https://oss.sonatype.org/service/local/";
    private static final String OSSRH_PASSWORD = "OSSRH_PASSWORD";
    private static final String OSSRH_USER = "OSSRH_USERNAME";

    public static Action<? super NexusRepositoryContainer> sonatypeRepo() {
        return c -> c.sonatype(nexusRepo(NEXUS_REPO_URL, SNAPSHOT_REPO_URL));
    }

    private static Action<? super NexusRepository> nexusRepo(String nexusRepoUrl, String snapshotRepoUrl) {
        return r -> {
            r.getNexusUrl().set(URI.create(nexusRepoUrl));
            r.getSnapshotRepositoryUrl().set(URI.create(snapshotRepoUrl));
            r.getUsername().set(System.getenv(OSSRH_USER));
            r.getPassword().set(System.getenv(OSSRH_PASSWORD));
        };
    }
}