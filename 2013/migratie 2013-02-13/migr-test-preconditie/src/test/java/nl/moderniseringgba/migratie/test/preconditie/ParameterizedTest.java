/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.preconditie;

import java.io.File;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.InsertBefore;
import nl.moderniseringgba.migratie.test.TestCasusFactory;
import nl.moderniseringgba.migratie.test.TestSuite;
import nl.moderniseringgba.migratie.test.util.EqualsFilter;
import nl.moderniseringgba.migratie.test.util.StartsWithFilter;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

/**
 * Geparameteriseerde test om makkelijk tests te kunnen runnen vanuit de assembly.
 */
public class ParameterizedTest extends AbstractTest {

    @Inject
    private ApplicationContext applicationContext;

    @Value("${test.directory:.\test}")
    private String testDirectory;

    @Value("${test.thema:}")
    private String thema;

    @Value("${test.casus:}")
    private String casus;

    @InsertBefore({ "/sql/data/dbunitConversieTabel.xml", "/sql/data/dbunitStamgegevens.xml" })
    @Test
    public void testMain() throws Exception {
        final TestCasusFactory factory =
                new PreconditieTestCasusFactory(applicationContext.getAutowireCapableBeanFactory());

        final TestSuite suite =
                new TestSuite(new File(testDirectory), new EqualsFilter("".equals(thema) ? null : thema),
                        new StartsWithFilter("".equals(casus) ? null : casus), factory);

        suite.run();
    }
}
