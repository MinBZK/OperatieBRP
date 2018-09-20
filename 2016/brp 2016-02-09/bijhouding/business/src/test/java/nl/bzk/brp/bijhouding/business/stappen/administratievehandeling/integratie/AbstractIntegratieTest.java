/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.administratievehandeling.integratie;

import nl.bzk.brp.dataaccess.test.DBUnitLoaderTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "/config/test-context.xml" })
@TestExecutionListeners(DBUnitLoaderTestExecutionListener.class)
public abstract class AbstractIntegratieTest extends AbstractJUnit4SpringContextTests {
}
