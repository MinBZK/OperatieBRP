/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3;

import java.io.File;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.InsertBefore;
import nl.moderniseringgba.migratie.test.TestCasusFactory;
import nl.moderniseringgba.migratie.test.TestSuite;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

public class ConversieTest extends AbstractTest {

    @Inject
    private ApplicationContext applicationContext;

    @InsertBefore({ "/sql/data/dbunitConversieTabel.xml", "/sql/data/dbunitStamgegevens.xml" })
    @Test
    public void testMain() throws Exception {
        final TestCasusFactory factory =
                new ConversieTestCasusFactory(applicationContext.getAutowireCapableBeanFactory());

        final TestSuite suite = new TestSuite(new File("./test"), null, null, factory);
        suite.run();
    }
}
