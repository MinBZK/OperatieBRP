package nl.bzk.brp.docker.dockerservice.mojo;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.docker.dockerservice.mojo.docker.DockerExecutor;
import nl.bzk.brp.docker.dockerservice.mojo.docker.DockerExecutor.DockerExecutionException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Mojo to remove all containers from a docker node.
 */
@Mojo(name = "remove-containers", requiresProject = false)
public final class RemoveContainersMojo extends AbstractMojo {

    /**
     * Extra docker argumenten. Kan bijvoorbeeld een alternatieve host (-H &lt;host>:&ltport>) of
     * TLS parameters bevatten.
     */
    @Parameter(property = "dockerArguments", defaultValue = "")
    private String dockerArguments;

    /** Filter. */
    @Parameter(property = "filter", defaultValue = "")
    private String filter;

    /** Ignore failures. */
    @Parameter(property = "ignoreFailures", defaultValue = "false")
    private boolean ignoreFailures;

    /**
     * Set docker arguments.
     *
     * @param dockerArguments docker arguments.
     */
    public void setDockerArguments(final String dockerArguments) {
        this.dockerArguments = dockerArguments;
    }

    /**
     * Set filter.
     *
     * @param filter filter
     */
    public void setFilter(final String filter) {
        this.filter = filter;
    }

    /**
     * Set ignore failures.
     *
     * @param ignoreFailures ignore failures.
     */
    public void setIgnoreFailures(final boolean ignoreFailures) {
        this.ignoreFailures = ignoreFailures;
    }

    @Override
    public void execute() throws MojoExecutionException {
        final DockerExecutor executor = new DockerExecutor(getLog()::info, getLog()::warn, dockerArguments);
        final List<String> containers;
        try {
            containers = listContainers(executor);
        } catch (final DockerExecutionException e) {
            getLog().warn("Kon lijst met containers niet opvragen: " + e.getMessage());
            if (ignoreFailures) {
                return;
            } else {
                throw new MojoExecutionException("Kon lijst met containers niet opvragen", e);
            }
        }

        for (final String container : containers) {
            if ("".equals(container.trim())) {
                continue;
            }
            try {
                removeContainer(executor, container);
            } catch (final DockerExecutionException e) {
                getLog().warn("Kon container '" + container + "' niet verwijderen: " + e.getMessage());
                if (!ignoreFailures) {
                    throw new MojoExecutionException("Kon container niet verwijderen", e);
                }
            }
        }
    }

    private void removeContainer(final DockerExecutor executor, final String container) throws DockerExecutionException {
        final List<String> argumenten = new ArrayList<>();
        argumenten.add("rm");
        argumenten.add("-vf");
        argumenten.add(container);

        executor.execute(argumenten);
    }

    private List<String> listContainers(final DockerExecutor executor) throws DockerExecutionException {
        final List<String> argumenten = new ArrayList<>();
        argumenten.add("ps");
        argumenten.add("-a");
        argumenten.add("-q");
        if ((filter != null) && !"".equals(filter)) {
            argumenten.add("--filter");
            argumenten.add(filter);
        }
        argumenten.add("--format");
        argumenten.add("{{.Names}}");

        return executor.execute(argumenten, true);
    }

}
