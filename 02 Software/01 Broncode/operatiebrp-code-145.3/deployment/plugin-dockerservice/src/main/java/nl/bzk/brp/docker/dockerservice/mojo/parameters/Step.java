package nl.bzk.brp.docker.dockerservice.mojo.parameters;

import java.util.List;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Define step in startup.
 */
public class Step {

    /**
     * Service list.
     */
    @Parameter(property = "services")
    private List<Service> services;

    /**
     * Docker-compose action
     */
    @Parameter(property = "action", required = true, defaultValue = "run")
    private String action = "run";

    /**
     * Delay after step.
     */
    @Parameter(property = "delay")
    private Integer delay;

    /**
     * Set services.
     *
     * @param services services
     */
    public void setServices(final List<Service> services) {
        this.services = services;
    }

    /**
     * Return services.
     *
     * @return services
     */
    public final List<Service> getServices() {
        return services;
    }

    /**
     * Set action.
     *
     * @param action
     */
    public void setAction(final String action) {
        this.action = action;
    }

    /**
     * Returns action.
     *
     * @return action
     */
    public final Action getAction() {
        return Action.valueOf(action.toUpperCase());
    }

    /**
     * Set delay.
     *
     * @param delay delay
     */
    public void setDelay(final Integer delay) {
        this.delay = delay;
    }

    /**
     * Returns delay.
     *
     * @return delay.
     */
    public final Integer getDelay() {
        return delay;
    }
}
