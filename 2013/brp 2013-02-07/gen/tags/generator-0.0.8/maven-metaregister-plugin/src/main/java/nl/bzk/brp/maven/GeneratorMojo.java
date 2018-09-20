/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.maven;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import nl.bzk.brp.generator.GenerationReport;
import nl.bzk.brp.generator.GenerationTargetModel;
import nl.bzk.brp.generator.GeneratorConfig;
import nl.bzk.brp.generator.ModelGenerator;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * Goal wordt gebruikt om generatie van java code, sql e.d. vanuit het BMR aan te sturen.
 * 
 * @goal generate
 * 
 * @phase generate-sources
 * @requiresDependencyResolution compile
 */
public class GeneratorMojo extends AbstractMojo {

    /**
     * Locatie van gegenereerde bestanden.
     * 
     * @parameter expression="${project.build.directory}/generated-sources"
     * @required
     */
    private File         outputDirectory;

    /**
     * Locatie van gegenereerde bestanden waarvan reeds gemodificeerde code bestond. Dit is een secondaire locatie om de
     * bestanden te plaatsen die niet overschreven konden worden.
     * 
     * @parameter expression="${project.build.directory}/generated-sources"
     * 
     */
    private File         userCodeSecondaryDirectory;

    /**
     * De target models.
     * 
     * @parameter default-value="java"
     * @required
     */
    private String       targetModel;

    /**
     * The generation elements, like enumations, attributetypes,.
     * 
     * @parameter
     */
    private List<String> generationElements;

    /**
     * Project instance, needed for attaching the buildinfo file.
     * Used to add new source directory to the build.
     * 
     * @parameter default-value="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.maven.plugin.AbstractMojo#execute()
     */
    @Override
    public void execute() throws MojoExecutionException {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(GeneratorConfig.class);

        ModelGenerator generator = ctx.getBean("javaModelGenerator", ModelGenerator.class);

        File generationTargetDirectory = outputDirectory;
        File reportDirectory = new File(project.getBuild().getDirectory());
        if (!generationTargetDirectory.exists()) {
            generationTargetDirectory.mkdirs();
        }

        File generationReport = new File(reportDirectory, "generation-report.txt");

        FileWriter w = null;
        List<GenerationReport> reports = null;
        try {
            w = new FileWriter(generationReport);

            Properties options = new Properties();
            options.put("verbose", true);

            GenerationTargetModel target = GenerationTargetModel.valueOf(targetModel.toUpperCase());

            reports =
                    generator.genereer(generationTargetDirectory.getAbsolutePath(),
                            userCodeSecondaryDirectory.getAbsolutePath(), target, generationElements, options);
            for (GenerationReport report : reports) {
                w.write(report.getObjectType() + "\n");
                w.write("============================\n");
                w.write("Gegenereerde objecten:\n");
                Map<String, String> success = report.getGeneratedObjects();
                for (Map.Entry<String, String> entry : success.entrySet()) {
                    w.write(entry.getKey() + "\n");
                }
                Map<String, String> failures = report.getErrors();
                if (report.getErrors().size() > 0) {
                    w.write("---------\n");
                    w.write("Fouten bij generatie van objecten:\n");

                    for (Map.Entry<String, String> entry : failures.entrySet()) {
                        w.write(entry.getKey() + ": " + entry.getValue() + "\n");
                    }
                }
            }

        } catch (IOException e) {
            throw new MojoExecutionException("Error creating file " + generationReport, e);
        } catch (MissingArgumentException e) {
            throw new MojoExecutionException("Fout bij het aanroepen van de generator. Te weinig argumenten.", e);
        } finally {
            if (w != null) {
                try {
                    w.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

    }
}
