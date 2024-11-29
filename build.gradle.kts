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

plugins {
    checkstyle
    `maven-publish`
}

subprojects {

//    afterEvaluate {
//
//        publishing {
//            publications.forEach { i ->
//                val mp = (i as MavenPublication)
//                mp.pom {
//                    name.set(project.name)
//                    description.set("Compliance Verification Toolkit")
//                    url.set("https://projects.eclipse.org/projects/technology.dataspacetck")
//
//                    licenses {
//                        license {
//                            name.set("The Apache License, Version 2.0")
//                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
//                        }
//                        developers {
//                            developer {
//                                id.set("JimMarino")
//                                name.set("Jim Marino")
//                                email.set("jmarino@metaformsystems.com")
//                            }
//                            developer {
//                                id.set("PaulLatzelsperger")
//                                name.set("Paul Latzelsperger")
//                                email.set("paul.latzelsperger@beardyinc.com")
//                            }
//                            developer {
//                                id.set("EnricoRisa")
//                                name.set("Enrico Risa")
//                                email.set("enrico.risa@gmail.com")
//                            }
//                        }
//                        scm {
//                            connection.set("scm:git:git@github.com:eclipse-dataspacetck/cvf.git")
//                            url.set("https://github.com/eclipse-dataspacetck/cvf.git")
//                        }
//                    }
//                }
//            }
//        }
//
//    }

}


// needed for running the dash tool
tasks.register("allDependencies", DependencyReportTask::class)

// disallow any errors
checkstyle {
    maxErrors = 0
}

allprojects{
    apply(plugin = "checkstyle")
    apply(plugin = "maven-publish")
    apply(plugin = "java-library")
}

subprojects {
    publishing {
        publications {
            if(project.subprojects.isEmpty()) {
                create<MavenPublication>(project.name) {
                    artifactId = project.name
                    from(components["java"])
                }
            }
        }
    }

    afterEvaluate {
        publishing {
            publications.forEach { i ->
                val mp = (i as MavenPublication)
                mp.pom {
                    name.set(project.name)
                    description.set("Compliance Verification Toolkit")
                    url.set("https://projects.eclipse.org/projects/technology.dataspacetck")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                        developers {
                            developer {
                                id.set("JimMarino")
                                name.set("Jim Marino")
                                email.set("jmarino@metaformsystems.com")
                            }
                            developer {
                                id.set("PaulLatzelsperger")
                                name.set("Paul Latzelsperger")
                                email.set("paul.latzelsperger@beardyinc.com")
                            }
                            developer {
                                id.set("EnricoRisa")
                                name.set("Enrico Risa")
                                email.set("enrico.risa@gmail.com")
                            }
                        }
                        scm {
                            connection.set("scm:git:git@github.com:eclipse-dataspacetck/cvf.git")
                            url.set("https://github.com/eclipse-dataspacetck/cvf.git")
                        }
                    }
                }
            }
        }
    }
}



