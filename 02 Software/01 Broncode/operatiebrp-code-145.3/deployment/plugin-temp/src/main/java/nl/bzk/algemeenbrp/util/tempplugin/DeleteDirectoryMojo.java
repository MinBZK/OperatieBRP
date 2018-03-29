package nl.bzk.algemeenbrp.util.tempplugin;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Delete directory mojo.
 */
@Mojo(name = "delete-directory")
public final class DeleteDirectoryMojo extends AbstractMojo {

    /** Directory to delete. */
    @Parameter(property = "directory")
    private File directory;

    /** Ignore failures. */
    @Parameter(property = "ignoreFailures", defaultValue = "true")
    private boolean ignoreFailures;

    /**
     * Set directory to delete.
     * 
     * @param directory directory to delete
     */
    public void setDirectory(final File directory) {
        this.directory = directory;
    }

    /**
     * Set ignore failures.
     * 
     * @param ignoreFailures ignore failures
     */
    public void setIgnoreFailures(final boolean ignoreFailures) {
        this.ignoreFailures = ignoreFailures;
    }

    @Override
    public void execute() throws MojoExecutionException {
        try {
            FileUtils.deleteDirectory(directory);
        } catch (final IOException e) {
            if (ignoreFailures) {
                getLog().info("Could not delete directory " + directory.toString(), e);
            } else {
                throw new MojoExecutionException("Could not delete directory " + directory.toString(), e);
            }
        }
    }
}
