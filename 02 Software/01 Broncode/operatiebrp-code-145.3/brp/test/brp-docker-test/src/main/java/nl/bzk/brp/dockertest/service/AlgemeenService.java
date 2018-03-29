/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service;

import java.util.Collection;
import java.util.List;
import nl.bzk.brp.dockertest.component.BrpOmgeving;
import nl.bzk.brp.dockertest.component.Docker;
import nl.bzk.brp.dockertest.component.DockerNaam;
import nl.bzk.brp.dockertest.component.LogfileAware;
import nl.bzk.brp.dockertest.component.ProcessHelper;
import nl.bzk.brp.dockertest.jbehave.JBehaveState;
import org.junit.Assert;

/**
 * AlgemeenService.
 */
public class AlgemeenService {

    private static final int WACHT_TIJD_MILLIS = 10_000;
    private final BrpOmgeving brpOmgeving;

    public AlgemeenService(final BrpOmgeving brpOmgeving) {
        this.brpOmgeving = brpOmgeving;
    }

    /**
     * Logregel validatie step.
     * @param regel de bedrijfsregel code
     * @param dockernaam naam van de Docker
     */
    public void isErEenLogregelGelogdMetRegel(String regel, DockerNaam dockernaam) {
        final List<String> commandList = brpOmgeving.getDockerCommandList("exec",
                brpOmgeving.geefDocker(dockernaam).getDockerContainerId(), "grep", regel, "logs/systeem.log");
        final String match = ProcessHelper.DEFAULT.startProces(commandList).geefOutput();
        Assert.assertTrue("Geen logregel gevonden met regel " + regel, match != null);
    }

    /**
     * Forceert alle Dockers te loggen naar de console.
     */
    public void doLog() throws InterruptedException {
        final Collection<Docker> dockers = JBehaveState.get().geefDockers();
        final Thread thread = new Thread(() ->
                dockers.stream().filter(docker -> docker instanceof LogfileAware).forEach(docker -> ((LogfileAware) docker).doLog()));
        thread.start();
        thread.join(WACHT_TIJD_MILLIS);
    }
}
