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

package org.eclipse.dataspacetck.gradle.tckbuild.extensions;

import org.gradle.api.Action;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.jvm.toolchain.JavaLanguageVersion;

public abstract class TckBuildExtension {
    private final PomExtension pom;

    public TckBuildExtension(ObjectFactory objectFactory) {
        pom = objectFactory.newInstance(PomExtension.class);
    }

    public void pom(Action<? super PomExtension> action) {
        action.execute(pom);
    }


    public PomExtension getPom() {
        return pom;
    }

    public abstract Property<JavaLanguageVersion> getJavaLanguageVersion();

    public abstract Property<Boolean> getPublish();
}
