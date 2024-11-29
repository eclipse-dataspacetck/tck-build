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

import org.gradle.api.provider.Property;

import java.util.HashSet;
import java.util.Set;

public abstract class DockerExtension {
    public static final String DEFAULT_DOCKERFILE_SUBPATH = "/src/main/docker/Dockerfile";

    private String additionalFiles = "build/legal/*";
    private final Set<String> imageTags = new HashSet<>(Set.of("latest"));

    public abstract Property<String> getDockerfilePath();

    public Set<String> getImageTags() {
        return imageTags;
    }

    public abstract Property<String> getPlatform();

    public abstract Property<String> getJarFilePath();


    public String getAdditionalFiles() {
        return additionalFiles;
    }

    public void setAdditionalFiles(String additionalFiles) {
        this.additionalFiles = additionalFiles;
    }
}
