/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.jmxcollector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ConnectiesTest {

    @Mock
    private Environment environment;

    @Test
    public void testBrpMessageBrokerConnection() throws Exception {
        final Connecties connecties = new Connecties(environment);
        Mockito.when(environment.getProperty("brp.messagebroker.jmx.service.username", "admin")).thenReturn("administrator");
        Mockito.when(environment.getProperty("brp.messagebroker.jmx.service.password", "admin")).thenReturn("adminadmin");
        Mockito.when(environment.getProperty("brp.messagebroker.jmx.service.host", "localhost")).thenReturn("eenhost");
        Mockito.when(environment.getProperty("brp.messagebroker.jmx.service.port", "3481")).thenReturn("42");

        Assert.notNull(connecties.getBrpMessagebrokerJmxConnector(), "Connector mag niet null zijn.");
    }

}


