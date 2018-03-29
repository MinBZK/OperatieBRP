/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MutatieleveringDockerIT extends TestCase {

    @Test
    public void testMaakMutatieleveringOmgeving() throws Exception {
        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.MUTATIELEVERING).build();
        omgeving.start();
        omgeving.cache().refresh();

        //FIXME dit geeft logs/systeem.log],  null[][][][][][][]  output
        //omgeving.algemeenService().isErEenLogregelGelogdMetRegel("ikbestaniet", "MUTATIELEVERING");

        final LogfileAware docker = omgeving.geefDocker(DockerNaam.MUTATIELEVERING);
        docker.doLog();

        omgeving.stop();
    }
}
