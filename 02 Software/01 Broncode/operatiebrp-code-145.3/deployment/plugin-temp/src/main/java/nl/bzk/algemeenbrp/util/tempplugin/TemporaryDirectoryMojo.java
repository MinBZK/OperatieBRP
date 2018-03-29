package nl.bzk.algemeenbrp.util.tempplugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Create temporary directory mojo.
 */
@Mojo(name = "temporary-directory")
public final class TemporaryDirectoryMojo extends AbstractMojo {

    /** Prefix. */
    @Parameter(property = "prefix")
    private String prefix;

    /** Property to store directory name in. */
    @Parameter(property = "propertyName", required = true)
    private String propertyName;

    /** Ignore failures. */
    @Parameter(property = "deleteOnExit", defaultValue = "true")
    private boolean deleteOnExit;

    /** Project. */
    @Parameter(property = "project", defaultValue = "${project}")
    private org.apache.maven.project.MavenProject project;

    @Override
    public void execute() throws MojoExecutionException {
        final String tempDirectory;
        try {
            final File directory = Files.createTempDirectory(prefix).toFile();
            if (deleteOnExit) {
                directory.deleteOnExit();
            }
            tempDirectory = directory.getCanonicalPath();
        } catch (final IOException e) {
            throw new MojoExecutionException("Cannot create temporary directory", e);
        }

        getLog().info("Setting property '" + propertyName + "' to temporary directory name '" + tempDirectory + "'.");
        project.getProperties().setProperty(propertyName, tempDirectory);
    }

}
