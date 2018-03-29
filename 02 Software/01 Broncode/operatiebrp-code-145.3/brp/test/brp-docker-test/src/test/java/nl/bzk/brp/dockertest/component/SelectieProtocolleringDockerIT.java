/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.io.IOException;
import javax.management.MalformedObjectNameException;
import org.junit.Test;

/**
 */
public class SelectieProtocolleringDockerIT {

    @Test
    public void test() throws InterruptedException, IOException, MalformedObjectNameException {
        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.SELECTIE_PROTOCOLLERING).build();
        omgeving.start();
        omgeving.stop();
    }
}
