/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.maven.plugin;

import java.io.File;
import java.io.IOException;

import nl.bzk.brp.bmr.generator.Main;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Goal which touches a timestamp file.
 *
 * @goal genereer
 * @phase generate-sources
 * @threadSafe
 * @requiresDependencyResolution compile
 */
public class BMRGeneratorMojo extends AbstractMojo {

    /**
     * URI van het XML bestand in 'bmrxmi' formaat, geproduceerd door EMF, met het bronmodel. Dit mag ook een
     * "classpath:" URI zijn.
     *
     * @parameter
     * @required
     */
    private String       modelUri;

    /**
     * De directory waarin gegenereerde source files worden geplaatst.
     *
     * @parameter expression="${project.build.directory}/generated-sources/bmr"
     * @required
     */
    private File         outputDirectory;

    /**
     * De directory waarin gecheckt wordt of een extension point al aanwezig is. De aanname is dat die dan handmatig
     * aangepast is.
     *
     * @parameter expression="${basedir}/src/main/java"
     * @required
     */
    private File         sourceDirectory;

    /**
     * De naam van het domein in het model waaruit code wordt gegenereerd.
     *
     * @parameter default-value="BRP"
     * @required
     */
    private String       domein;

    /**
     * De namen van de generatoren die worden gebruikt.
     *
     * @parameter
     * @required
     */
    private String[]     generatoren;

    /**
     * Project instance, needed for attaching the buildinfo file.
     * Used to add new source directory to the build.
     *
     * @parameter default-value="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    @Override
    public final void execute() throws MojoExecutionException {
        File f = outputDirectory;

        if (!f.exists()) {
            f.mkdirs();
        }
        project.addCompileSourceRoot(outputDirectory.getAbsolutePath());
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:generator-beans.xml");
        String uri = null;
        try {
            uri = context.getResource(modelUri).getURI().toString();
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage());
        }
        Main main = context.getBean(Main.class);
        main.genereer(uri, domein, outputDirectory.getAbsolutePath(), sourceDirectory.getAbsolutePath(), generatoren);
    }
}
