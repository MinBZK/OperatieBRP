/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.jmxcollector;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test voor metingen
 */
@RunWith(MockitoJUnitRunner.class)
public class MetingenTest {

    @Test
    public void test() throws Exception {

        final Metingen metingen = new Metingen();

        Assert.assertEquals(2, metingen.getMetingenMap().keySet().size());
        Assert.assertEquals(16, metingen.getMetingenMap().get("brpMessagebrokerJmxConnector").size());
    }

}
