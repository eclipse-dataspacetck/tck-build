package org.eclipse.dataspacetck.gradle.plugins.tckbuild;


import org.eclipse.dataspacetck.gradle.tasks.GenerateTestPlanTask;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

import static java.util.Optional.ofNullable;

/**
 * Gradle plugin that customized the build for TCK projects
 */
public class TckBuildPlugin implements org.gradle.api.Plugin<Project> {


    @Override
    public void apply(@NotNull Project target) {
        // register the annotation processor task to all modules that declare a dependency onto the annotation processor,
        // except the annotation processor module itself
        if (target.getProperties().containsKey("tck.diagrams.generate")) {

            ofNullable(target.getConfigurations().findByName("annotationProcessor"))
                    .ifPresent(c -> c.getDependencies().add(target.getDependencies().create("org.eclipse.dataspacetck:diagram-generator:0.0.1")));

            target.getTasks().register(GenerateTestPlanTask.NAME, GenerateTestPlanTask.class).configure(generateTestPlanTask -> {
                generateTestPlanTask.setGroup("documentation");
                // normally one would use getOrDefault, but the wildcard map prevents that
                generateTestPlanTask.setImageFormat(ofNullable(target.getProperties().get("cvf.conversion.format")).map(Object::toString).orElse(("svg")));
                generateTestPlanTask.setForceConversion(ofNullable(target.getProperties().get("cvf.conversion.force")).map(Object::toString).map(Boolean::parseBoolean).orElse(true));
                // generateTestPlanTask.setOutputDirectory("/path/to/directory");
            });
        }
    }
}
