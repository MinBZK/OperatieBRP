/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.isc;

import java.io.File;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.test.TestCasusFactory;
import nl.moderniseringgba.migratie.test.TestSuite;
import nl.moderniseringgba.migratie.test.isc.environment.TestEnvironment;
import nl.moderniseringgba.migratie.test.util.EqualsFilter;
import nl.moderniseringgba.migratie.test.util.StartsWithFilter;

import org.junit.Test;

public class SingleTest extends AbstractTest {

    @Inject
    private TestEnvironment environment;

    @Test
    public void testMain() throws Exception {
        final TestCasusFactory factory = new ProcessenTestCasusFactory(environment);

        final TestSuite suite =
                new TestSuite(new File("./test"), new EqualsFilter("uc311"), new StartsWithFilter("happy-flow"),
                        factory);

        suite.run();
    }
}
