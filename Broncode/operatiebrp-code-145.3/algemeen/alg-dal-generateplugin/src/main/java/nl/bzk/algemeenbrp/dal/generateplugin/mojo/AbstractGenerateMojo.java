/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.generateplugin.mojo;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Basis generator mojo.
 */
public abstract class AbstractGenerateMojo extends AbstractMojo {

    /**
     * Source directory.
     */
    @Parameter(property = "source", defaultValue = "${project.basedir}/src/main/templates/properties")
    private File source;

    /**
     * Destination directory.
     */
    @Parameter(property = "destination", defaultValue = "${project.basedir}/target/generated/properties")
    private File destination;

    /**
     * Destination directory.
     */
    @Parameter(property = "packageName", defaultValue = "")
    private String packageName;

    /**
     * Zet bron directory.
     * @param source bron directory
     */
    public void setSource(final File source) {
        this.source = source;
    }

    /**
     * Zet doel directory.
     * @param destination doel directory
     */
    public void setDestination(final File destination) {
        this.destination = destination;
    }

    /**
     * Zet package naam.
     * @param packageName package naam
     */
    public void setPackageName(final String packageName) {
        this.packageName = packageName;
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        // Create test context
        try (final GenericXmlApplicationContext generateContext = new GenericXmlApplicationContext()) {
            generateContext.load("classpath:generate-context.xml");
            generateContext.refresh();

            final DataSource dataSource = generateContext.getBean(DataSource.class);
            final Generator generator = new Generator(getLog(), this::processRecord);

            getLog().info("Loading templates from: " + source.getAbsolutePath());
            getLog().info("Package: " + packageName);
            for (final File file : source.listFiles(this::acceptAllFiles)) {
                generator.generate(dataSource, file, destination, packageName);
            }
        }
    }

    private boolean acceptAllFiles(final File file) {
        return file.isFile();
    }

    protected abstract void processRecord(final StringBuilder result, final ResultSet resultSet) throws SQLException;
}
