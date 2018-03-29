/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.jms.JMSException;
import org.junit.Test;

/**
 */
public class BeheerDockerSelectieFrontendIT {

    @Test
    public void test() throws InterruptedException, JMSException, IOException {
        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.BEHEER_SELECTIE_FRONTEND).build();
        omgeving.start();
        TimeUnit.HOURS.sleep(1);
        omgeving.stop();
    }

}
