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

import io.github.gradlenexus.publishplugin.NexusPublishExtension;
import org.gradle.api.Project;

import static org.eclipse.dataspacetck.gradle.Repositories.sonatypeRepo;

public class NexusPublishingConvention {

    public void apply(Project target) {
        if (target == target.getRootProject()) {


            target.getExtensions().configure(NexusPublishExtension.class, nexusPublishExtension -> {
                nexusPublishExtension.repositories(sonatypeRepo());
            });
        }
    }
}
