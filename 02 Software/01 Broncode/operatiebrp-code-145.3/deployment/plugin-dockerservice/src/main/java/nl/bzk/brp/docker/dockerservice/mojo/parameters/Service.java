package nl.bzk.brp.docker.dockerservice.mojo.parameters;

import org.apache.maven.plugins.annotations.Parameter;

/**
 * Docker service.
 */
public class Service {

    /**
     * Alternatieve docker argumenten. Kan bijvoorbeeld een alternatieve host (-H
     * &lt;host>:&ltport>) of TLS parameters bevatten..
     */
    @Parameter(property = "overrideDockerArguments", defaultValue = "")
    private String overrideDockerArguments;

    /** service name. */
    @Parameter(property = "name", required = true)
    private String name;

    /** Version of the needed image. */
    @Parameter(property = "name", required = true, defaultValue = "latest")
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
     * Set override docker arguments.
     *
     * @param overrideDockerArguments overridden docker arguments
     */
    public void setOverrideDockerArguments(final String overrideDockerArguments) {
        this.overrideDockerArguments = overrideDockerArguments;
    }

    /**
     * @return alternatieve docker argumenten.
     */
    public String getOverrideDockerArguments() {
        return overrideDockerArguments;
    }

    /**
     * Set name.
     *
     * @param name name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return service name.
     */
    public final String getName() {
        return name;
    }

    /**
     * Set image version.
     *
     * @param version image version
     */
    public void setVersion(final String version) {
        this.version = version;
    }

    /**
     * @return version of the needed image.
     */
    public final String getVersion() {
        return version;
    }

    /**
     * Set service arguments.
     *
     * @param serviceArguments service arguments
     */
    public void setServiceArguments(final String serviceArguments) {
        this.serviceArguments = serviceArguments;
    }

    /**
     * @return extra arguments for a service
     */
    public final String getServiceArguments() {
        return serviceArguments;
    }

    /**
     * Set skip port mapping.
     *
     * @param skipPortMapping skip port mapping
     */
    public void setSkipPortMapping(boolean skipPortMapping) {
        this.skipPortMapping = skipPortMapping;
    }

    /**
     * @return skip port mapping
     */
    public boolean getSkipPortMapping() {
        return skipPortMapping;
    }

    /**
     * Set command arguments.
     *
     * @param commandArguments command arguments
     */
    public void setCommandArguments(final String commandArguments) {
        this.commandArguments = commandArguments;
    }

    /**
     * @return extra arguments for a command
     */
    public final String getCommandArguments() {
        return commandArguments;
    }
}
