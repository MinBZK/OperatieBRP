package nl.bzk.brp.docker.dockerservice.mojo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.docker.dockerservice.mojo.docker.DockerExecutor;
import nl.bzk.brp.docker.dockerservice.mojo.docker.DockerExecutor.DockerExecutionException;
import nl.bzk.brp.docker.dockerservice.mojo.parameters.Action;
import nl.bzk.brp.docker.dockerservice.mojo.service.ServiceUtil;
import nl.bzk.brp.docker.dockerservice.mojo.service.ServiceUtil.ServiceConfigurationException;
import nl.bzk.brp.docker.dockerservice.mojo.service.ServicesUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Pull for release haalt images op uit registry en verwijderd de registry informatie van de images.
 */
@Mojo(name = "pull-for-release")
public final class PullForReleaseMojo extends AbstractMojo {

    private static final long MILLISECONDS = 1000;

    /**
     * Extra docker argumenten. Kan bijvoorbeeld een alternatieve host (-H &lt;host>:&ltport>) of
     * TLS parameters bevatten.
     */
    @Parameter(property = "dockerArguments", defaultValue = "")
    private String dockerArguments;

    /**
     * Registry.
     */
    @Parameter(property = "registry", defaultValue = "")
    private String registry;

    /**
     * Version.
     */
    @Parameter(property = "version", defaultValue = "latest")
    private String version;

    /**
     * Ignore failures.
     */
    @Parameter(property = "ignoreFailures", defaultValue = "false")
    private boolean ignoreFailures;

    /**
     * Set docker arguments.
     * @param dockerArguments docker arguments
     */
    public void setDockerArguments(final String dockerArguments) {
        this.dockerArguments = dockerArguments;
    }

    /**
     * Set registry.
     * @param registry registry
     */
    public void setRegistry(final String registry) {
        this.registry = registry;
    }

    /**
     * Set version.
     * @param version versie van de images
     */
    public void setVersion(final String version) {
        this.version = version;
    }

    /**
     * Set ignore failures.
     * @param ignoreFailures ignore failures
     */
    public void setIgnoreFailures(final boolean ignoreFailures) {
        this.ignoreFailures = ignoreFailures;
    }

    @Override
    public void execute() throws MojoExecutionException {
        if (registry != null && !registry.equals("")) {
            final DockerExecutor baseExecutor = new DockerExecutor(getLog()::info, getLog()::warn, dockerArguments);

            try {
                for (final String serviceNaam : ServicesUtil.bepaalAlleServices()) {
                    executeStep(baseExecutor, serviceNaam);
                }
            } catch (IOException e) {
                throw new MojoExecutionException("Kon services niet bepalen", e);
            }

        } else {
            throw new MojoExecutionException("Release onmogelijk van lokaal systeem");
        }
    }


    private void executeStep(final DockerExecutor baseExecutor, final String serviceNaam) throws MojoExecutionException {
        haalImageOpUitRegistry(serviceNaam, baseExecutor);
        verwijderRegistryUitImageNaam(serviceNaam, baseExecutor);
    }

    private void haalImageOpUitRegistry(final String serviceNaam, final DockerExecutor executor) throws MojoExecutionException {
        final List<String> argumentenLijst = new ArrayList<>();
        try {
            argumentenLijst.add(Action.PULL.getPropertyKey());
            argumentenLijst.add(ServiceUtil.bepaalImage(serviceNaam, Action.PULL, registry, version));
        } catch (final ServiceConfigurationException e) {
            throw new MojoExecutionException("Kan service configuratie niet bepalen", e);
        }

        try {
            executor.execute(argumentenLijst);
        } catch (final DockerExecutionException e) {
            if (!ignoreFailures) {
                throw new MojoExecutionException(e.getMessage(), e);
            } else {
                getLog().info("Docker command failed, but ignoring failures");
            }
        }
    }

    private void verwijderRegistryUitImageNaam(final String serviceNaam, final DockerExecutor executor) throws MojoExecutionException {
        final List<String> argumentenLijst = new ArrayList<>();
        try {
            argumentenLijst.add("tag");
            argumentenLijst.add(ServiceUtil.bepaalImage(serviceNaam, Action.PULL, registry, version));
            argumentenLijst.add(ServiceUtil.bepaalImage(serviceNaam, Action.PULL, null, version));
        } catch (final ServiceConfigurationException e) {
            throw new MojoExecutionException("Kan service configuratie niet bepalen", e);
        }

        try {
            executor.execute(argumentenLijst);
        } catch (final DockerExecutionException e) {
            if (!ignoreFailures) {
                throw new MojoExecutionException(e.getMessage(), e);
            } else {
                getLog().info("Docker command failed, but ignoring failures");
            }
        }
    }
}
