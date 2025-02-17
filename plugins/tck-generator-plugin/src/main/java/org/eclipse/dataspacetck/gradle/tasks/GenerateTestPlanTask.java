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

package org.eclipse.dataspacetck.gradle.tasks;

import org.eclipse.dataspacetck.annotation.processors.TestPlanGenerator;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.work.InputChanges;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

/**
 * Task to explicitly invoke an annotation processor that generates the test plan document. To enable this task, the {@code TestPlanGenerator}
 * must be on the class path, e.g.:
 * <pre>
 * //yourModule/build.gradle.kts
 *
 * dependencies {
 *     annotationProcessor(project(":tools"))
 * }
 * tasks.register&lt;GenerateTestPlanTask&gt;("genTestPlan") {
 *     imageFormat = "png"
 *     forceConversion = true
 *     outputDirectory = "/path/to/your/output/dir"
 * }
 * </pre>
 */
public class GenerateTestPlanTask extends JavaCompile {
    public static final String NAME = "genTestPlan";
    private static final List<String> ALLOWED_FORMATS = List.of("png", "svg");
    private String imageFormat = "svg";
    private boolean forceConversion = true;
    private String outputDirectory;

    public GenerateTestPlanTask() {
        outputDirectory = getProject().getRootProject().getLayout().getBuildDirectory().getAsFile().get().getAbsolutePath();
        source(getProject().fileTree("src/main/java"));

        setClasspath(getProject().getExtensions().getByType(SourceSetContainer.class).getByName("main").getRuntimeClasspath());

        getDestinationDirectory().set(getProject().getLayout().getBuildDirectory().dir("generated/source/annotationProcessor"));

        getOptions().setAnnotationProcessorPath(getProject().getConfigurations().getByName("annotationProcessor"));
    }

    /**
     * Description of the task, used for {@code ./gradlew help --task genTestPlan}
     */
    @Override
    public @NotNull String getDescription() {
        return "Generates a test plan by processing test annotations. Use the properties of this task to control image rendering.";
    }

    /**
     * The image format that diagrams are converted into. This property is ignored when {@link GenerateTestPlanTask#setForceConversion(boolean)} is false.
     * Possible values are "png" and "svg"
     */
    @Input
    public String getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(String imageFormat) {
        imageFormat = imageFormat.toLowerCase().trim();
        if (ALLOWED_FORMATS.contains(imageFormat)) {
            this.imageFormat = imageFormat;
        } else {
            throw new IllegalArgumentException(imageFormat + " is not a valid image format, please use one of " + ALLOWED_FORMATS);
        }
    }

    /**
     * Whether all diagrams (mermaid, plantuml) should be converted to - and embedded as - image, or directly as Mermaid/PlantUML code
     */
    @Input
    public boolean getForceConversion() {
        return forceConversion;
    }

    public void setForceConversion(boolean forceConversion) {
        this.forceConversion = forceConversion;
    }

    /**
     * Optionally sets the output directory for the generated document.
     */
    @Input
    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    /**
     * Task output: where the test plan document is put
     */
    @OutputDirectory
    public File getOutputDir() {
        return new File(outputDirectory);
    }

    @Override
    protected void compile(InputChanges inputs) {
        if (forceConversion) {
            getLogger().info("Will convert all diagrams to {} images and embed those.", imageFormat);
        } else {
            getLogger().info("Will directly embed diagram source code in the test plan document.");
        }
        // add compiler arguments lazily, when all properties are sure to have been assigned.
        getOptions().getCompilerArgs().addAll(
                List.of("-processor", TestPlanGenerator.class.getName(),
                        "-Acvf.outputDir=" + outputDirectory, //set output path where testplan.md is stored
                        "-Acvf.conversion.force=" + forceConversion, // force pre-rendering of mermaid/plantuml diagrams as images (as opposed to: direct embed)
                        "-Acvf.conversion.format=" + imageFormat, // image format for pre-rendering
                        "-Acvf.generate=true" // enable. always true.
                )
        );
        super.compile(inputs);
        getLogger().lifecycle("Test plan document generated at {}{}testplan.md", getOutputDir(), File.separator);
    }
}
