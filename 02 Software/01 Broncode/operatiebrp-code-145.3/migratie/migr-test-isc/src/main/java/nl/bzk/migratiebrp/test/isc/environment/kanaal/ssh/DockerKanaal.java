/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */


package nl.bzk.migratiebrp.test.isc.environment.kanaal.ssh;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.docker.dockerservice.mojo.docker.DockerExecutor;
import nl.bzk.brp.docker.dockerservice.mojo.docker.DockerExecutor.DockerExecutionException;
import nl.bzk.brp.docker.dockerservice.mojo.parameters.Action;
import nl.bzk.brp.docker.dockerservice.mojo.parameters.Mode;
import nl.bzk.brp.docker.dockerservice.mojo.service.ServiceUtil;
import nl.bzk.brp.docker.dockerservice.mojo.service.ServiceUtil.ServiceConfigurationException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import org.springframework.beans.factory.annotation.Value;

/**
 * Uitvoeren van oude ssh commando's via docker containers.
 */
public class DockerKanaal extends AbstractKanaal {

    /**
     * Kanaal naam.
     */
    public static final String KANAAL = "docker";

    private static final Logger LOG = LoggerFactory.getLogger();

    private boolean dockerEnabled;

    private String dockerHost;
    private String imageRegistry;
    private String imageVersion;

    /**
     * Docker enabled.
     * @param runningDocker dockerEnabled
     */
    @Value("${docker.enabled:false}")
    public final void setDockerEnabled(final boolean runningDocker) {
        dockerEnabled = runningDocker;
    }
    /**
     * Docker host.
     * @param dockerHost docker host
     */
    @Value("${docker.host:}")
    public final void setDockerHost(final String dockerHost) {
        this.dockerHost = dockerHost;
    }

    /**
     * Image registry.
     * @param imageRegistry image registry
     */
    @Value("${docker.image.registry:}")
    public final void setImageRegistry(final String imageRegistry) {
        this.imageRegistry = imageRegistry;
    }

    /**
     * Image version.
     * @param imageVersion image version
     */
    @Value("${docker.image.version:}")
    public final void setImageVersion(final String imageVersion) {
        this.imageVersion = imageVersion;
    }

    @Override
    public final String getKanaal() {
        return KANAAL;
    }

    @Override
    public final void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
        if (dockerEnabled) {
            final String[] dockerLines = bericht.getInhoud().split("\\r?\\n");

            final String serviceNaam = dockerLines[0];
            if (serviceNaam.split(" ").length > 1) {
                throw new KanaalException("Bericht mag alleen de servicenaam bevatten");
            }
            final String serviceArguments = dockerLines.length > 1 ? dockerLines[1] : null;
            final String commandArguments = dockerLines.length > 2 ? dockerLines[2] : null;

            final List<String> argumentenLijst = new ArrayList<>();
            try {
                argumentenLijst.addAll(Mode.RUN.createCommand(Action.EXECUTE, ServiceUtil.bepaalContainerNaam(serviceNaam)));
                argumentenLijst
                        .addAll(ServiceUtil
                                .bepaalActieArgumenten(serviceNaam, Action.EXECUTE, Mode.RUN, imageRegistry, imageVersion, serviceArguments, false, commandArguments));
            } catch (final ServiceConfigurationException e) {
                throw new KanaalException("Kan service configuratie niet bepalen", e);
            }

            try {
                final String dockerArguments = dockerHost == null || "".equals(dockerHost.trim()) ? "" : "-H " + dockerHost;
                final DockerExecutor executor = new DockerExecutor(LOG::info, LOG::warn, dockerArguments);
                executor.execute(argumentenLijst);
            } catch (final DockerExecutionException e) {
                throw new KanaalException(e.getMessage(), e);
            }
        }
    }
}
