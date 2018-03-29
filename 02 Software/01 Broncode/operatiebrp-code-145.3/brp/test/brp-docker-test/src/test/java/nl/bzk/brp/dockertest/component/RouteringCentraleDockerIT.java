/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import javax.jms.JMSException;
import javax.jms.Message;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.util.Assert;

@RunWith(JUnit4.class)
public class RouteringCentraleDockerIT {

    @Test
    public void test() throws InterruptedException {
        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.ROUTERINGCENTRALE).build();
        omgeving.start();
        omgeving.stop();
    }

    @Test
    public void test2() throws InterruptedException, JMSException {
        final BrpOmgeving omgeving = new OmgevingBuilder().metTopLevelDockers(DockerNaam.ROUTERINGCENTRALE).build();
        omgeving.start();
        final JmsDocker component = omgeving.geefDocker(DockerNaam.ROUTERINGCENTRALE);
        final ActiveMQQueue activeMQQueue = new ActiveMQQueue("xxx");
        component.voerUit(
                sessie -> sessie.convertAndSend(activeMQQueue, "xxx"));
        component.voerUit(sessie -> {
            sessie.setReceiveTimeout(10000);
            final Message message = sessie.receive(activeMQQueue);
            Assert.notNull(message);
        });

        omgeving.stop();
    }
}
