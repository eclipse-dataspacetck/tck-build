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

import org.gradle.api.Project;
import org.gradle.api.provider.Property;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public abstract class PomExtension {

    private List<DeveloperContact> developerContacts;

    @Inject
    public PomExtension(Project project) {
        developerContacts = new ArrayList<>();
        developerContacts.add(new DeveloperContact("jimmarino", "Jim Marino", "jmarino@metaformsystems.com"));
        developerContacts.add(new DeveloperContact("paullatzelsperger", "Paul Latzelsperger", "paul.latzelsperger@beardyinc.com"));
        developerContacts.add(new DeveloperContact("wolf4ood", "Enrico Risa", "enrico.risa@gmail.com"));
        developerContacts.add(new DeveloperContact("ndr-brt", "Andrea Bertagnolli", "andrea.bertagnolli@gmail.com"));
    }

    public abstract Property<String> getProjectName();

    public abstract Property<String> getDescription();

    public abstract Property<String> getProjectUrl();

    public abstract Property<String> getLicenseName();

    public abstract Property<String> getLicenseUrl();

    public List<DeveloperContact> getDeveloper() {
        return developerContacts;
    }

    public void setDeveloper(List<DeveloperContact> developer) {
        developerContacts = developer;
    }

    public abstract Property<String> getScmConnection();

    public abstract Property<String> getScmUrl();

}
