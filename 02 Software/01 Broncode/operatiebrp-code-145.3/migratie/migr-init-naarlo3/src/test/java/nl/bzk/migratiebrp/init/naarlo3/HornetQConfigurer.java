/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarlo3;

import java.util.List;
import org.hornetq.api.core.SimpleString;
import org.hornetq.core.server.HornetQServer;
import org.hornetq.core.server.embedded.EmbeddedHornetQ;
import org.hornetq.jms.client.HornetQDestination;
import org.hornetq.spi.core.security.HornetQSecurityManager;
import org.springframework.beans.factory.InitializingBean;

public class HornetQConfigurer implements InitializingBean {

    public void setQueues(final List<String> queues) {
        this.queues = queues;
    }

    public void setEmbeddedHornetQ(final EmbeddedHornetQ embeddedHornetQ) {
        this.embeddedHornetQ = embeddedHornetQ;
    }

    private List<String> queues;
    private EmbeddedHornetQ embeddedHornetQ;

    @Override
    public void afterPropertiesSet() throws Exception {
        final HornetQServer server = embeddedHornetQ.getHornetQServer();

        // (Default)User
        final HornetQSecurityManager securityManager = server.getSecurityManager();
        securityManager.setDefaultUser("guest");
        securityManager.addUser("guest", "guest");
        securityManager.addRole("guest", "guest");

        // Queues
        for (final String queue : queues) {
            server.deployQueue(
                    SimpleString.toSimpleString(HornetQDestination.JMS_QUEUE_ADDRESS_PREFIX + queue),
                    SimpleString.toSimpleString(HornetQDestination.JMS_QUEUE_ADDRESS_PREFIX + queue),
                    SimpleString.toSimpleString(""),
                    true,
                    false);
        }
    }
}
