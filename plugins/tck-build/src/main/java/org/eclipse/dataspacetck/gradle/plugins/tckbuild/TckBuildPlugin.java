package org.eclipse.dataspacetck.gradle.plugins.tckbuild;


import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gradle plugin that customized the build for TCK projects
 */
public class TckBuildPlugin implements org.gradle.api.Plugin<Project> {

    private final Logger log = LoggerFactory.getLogger(TckBuildPlugin.class);

    @Override
    public void apply(@NotNull Project target) {
        log.info("Applying TckBuildPlugin to project: {}", target.getName());
    }
}
