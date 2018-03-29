package nl.bzk.brp.docker.dockerservice.mojo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import nl.bzk.brp.docker.dockerservice.mojo.docker.DockerExecutor;
import nl.bzk.brp.docker.dockerservice.mojo.docker.DockerExecutor.DockerExecutionException;
import nl.bzk.brp.docker.dockerservice.mojo.parameters.Mode;
import nl.bzk.brp.docker.dockerservice.mojo.parameters.Service;
import nl.bzk.brp.docker.dockerservice.mojo.parameters.Step;
import nl.bzk.brp.docker.dockerservice.mojo.service.ServiceUtil;
import nl.bzk.brp.docker.dockerservice.mojo.service.ServiceUtil.ServiceConfigurationException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Run mojo.
 */
@Mojo(name = "run")
public final class RunMojo extends AbstractMojo {

    private static final long MILLISECONDS = 1000;

    /**
     * Extra docker argumenten. Kan bijvoorbeeld een alternatieve host (-H &lt;host>:&ltport>) of
     * TLS parameters bevatten.
     */
    @Parameter(property = "dockerArguments", defaultValue = "")
    private String dockerArguments;

    /**
     * Mode.
     */
    @Parameter(property = "mode", required = true, defaultValue = "run")
    private String mode;

    /**
     * Registry.
     */
    @Parameter(property = "registry", defaultValue = "")
    private String registry;

    /**
     * Steps.
     */
    @Parameter(property = "steps")
    private List<Step> steps;

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
     * Set mode.
     * @param mode mode
     */
    public void setMode(final String mode) {
        this.mode = mode;
    }

    /**
     * Set registry.
     * @param registry registry
     */
    public void setRegistry(final String registry) {
        this.registry = registry;
    }

    /**
     * Set steps.
     * @param steps steps
     */
    public void setSteps(final List<Step> steps) {
        this.steps = steps;
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
        final DockerExecutor baseExecutor = new DockerExecutor(getLog()::info, getLog()::warn, dockerArguments);

        for (final Step step : steps) {
            for (final Service service : step.getServices()) {
                executeStep(baseExecutor, step, service);
            }

            if (step.getDelay() != null) {
                try {
                    getLog().info(String.format("Waiting for %s seconds", step.getDelay()));
                    TimeUnit.MILLISECONDS.sleep(step.getDelay() * MILLISECONDS);
                } catch (final InterruptedException ex) {
                    // ignore
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private void executeStep(final DockerExecutor baseExecutor, final Step step, final Service service) throws MojoExecutionException {
        final DockerExecutor executor;
        if ((service.getOverrideDockerArguments() == null) || service.getOverrideDockerArguments().isEmpty()) {
            executor = baseExecutor;
        } else {
            executor = new DockerExecutor(getLog()::info, getLog()::warn, service.getOverrideDockerArguments());
        }

        final List<String> argumentenLijst = new ArrayList<>();
        try {
            argumentenLijst.addAll(getMode().createCommand(step.getAction(), ServiceUtil.bepaalContainerNaam(service.getName())));
            argumentenLijst.addAll(ServiceUtil.bepaalActieArgumenten(service.getName(), step.getAction(), getMode(), registry, service.getVersion(),
                    service.getServiceArguments(), service.getSkipPortMapping(), service.getCommandArguments()));
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

    private Mode getMode() {
        return Mode.valueOf(mode.toUpperCase());
    }

}
