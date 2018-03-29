package nl.bzk.brp.docker.dockerservice.mojo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import nl.bzk.brp.docker.dockerservice.mojo.docker.DockerExecutor;
import nl.bzk.brp.docker.dockerservice.mojo.docker.DockerExecutor.DockerExecutionException;
import nl.bzk.brp.docker.dockerservice.mojo.parameters.Action;
import nl.bzk.brp.docker.dockerservice.mojo.parameters.Mode;
import nl.bzk.brp.docker.dockerservice.mojo.service.ServiceUtil;
import nl.bzk.brp.docker.dockerservice.mojo.service.ServiceUtil.ServiceConfigurationException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Single mojo.
 */
@Mojo(name = "single", requiresProject = false)
public final class SingleMojo extends AbstractMojo {

    private static final int MILLISECONDS = 1000;

    /**
     * Extra docker argumenten. Kan bijvoorbeeld een alternatieve host (-H &lt;host>:&ltport>) of
     * TLS parameters bevatten..
     */
    @Parameter(property = "dockerArguments", defaultValue = "")
    private String dockerArguments;

    /**
     * Mode.
     */
    @Parameter(property = "mode", required = true, defaultValue = "run")
    private String mode;

    /**
     * Action.
     */
    @Parameter(property = "action", required = true, defaultValue = "run")
    private String action;

    /**
     * Action.
     */
    @Parameter(property = "service", required = true)
    private String service;

    /**
     * Registry.
     */
    @Parameter(property = "registry", defaultValue = "")
    private String registry;

    /**
     * Version.
     */
    @Parameter(property = "version", required = true, defaultValue = "latest")
    private String version;

    /**
     * Extra service argumenten. Kan bijvoorbeeld constraints (--constraint
     * engine.labels.nl.bzk.rol==migrdb) bevatten.
     */
    @Parameter(property = "serviceArguments", defaultValue = "")
    private String serviceArguments;

    /**
     * Skip port mapping.
     */
    @Parameter(property = "skipPortMapping", defaultValue = "false")
    private boolean skipPortMapping;

    /**
     * Extra commando argumenten (met dit commando wordt het uit te voeren commando binnen de image
     * bedoeld).
     */
    @Parameter(property = "commandArguments", defaultValue = "")
    private String commandArguments;

    /**
     * Delay.
     */
    @Parameter(property = "delay")
    private Integer delay;

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
     * Set action.
     * @param action action
     */
    public void setAction(final String action) {
        this.action = action;
    }

    /**
     * Set service.
     * @param service service
     */
    public void setService(final String service) {
        this.service = service;
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
     * @param version version
     */
    public void setVersion(final String version) {
        this.version = version;
    }

    /**
     * Set service arguments.
     * @param serviceArguments service arguments
     */
    public void setServiceArguments(final String serviceArguments) {
        this.serviceArguments = serviceArguments;
    }

    /**
     * Set command arguments.
     * @param commandArguments command arguments.
     */
    public void setCommandArguments(final String commandArguments) {
        this.commandArguments = commandArguments;
    }

    /**
     * Set delay.
     * @param delay delay
     */
    public void setDelay(final Integer delay) {
        this.delay = delay;
    }

    @Override
    public void execute() throws MojoExecutionException {
        final DockerExecutor executor = new DockerExecutor(getLog()::info, getLog()::warn, dockerArguments);

        final List<String> argumentenLijst = new ArrayList<>();
        try {
            argumentenLijst.addAll(getMode().createCommand(getAction(), ServiceUtil.bepaalContainerNaam(service)));
            argumentenLijst.addAll(ServiceUtil
                    .bepaalActieArgumenten(service, getAction(), getMode(), registry, version, serviceArguments, skipPortMapping, commandArguments));
        } catch (final ServiceConfigurationException e) {
            throw new MojoExecutionException("Kan service configuratie niet bepalen", e);
        }

        try {
            executor.execute(argumentenLijst);
        } catch (final DockerExecutionException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }

        if (delay != null) {
            for (int wait = delay; wait > 0; wait--) {
                try {
                    getLog().info(String.format("Waiting for %s seconds ...", wait));
                    TimeUnit.MILLISECONDS.sleep(MILLISECONDS);
                } catch (final InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private Action getAction() {
        return Action.valueOf(action.toUpperCase());
    }

    private Mode getMode() {
        return Mode.valueOf(mode.toUpperCase());
    }

}
