/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.integratie;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


@ContextConfiguration(locations = { "/config/integratieTest-context.xml" })
public abstract class AbstractIntegratieTest extends AbstractJUnit4SpringContextTests {

    public static final String TEST_SERVER_CONTEXT = "http://localhost:8181/dashboard-restapi";
}
