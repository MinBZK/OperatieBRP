/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.isc.helper;

import java.io.File;
import nl.bzk.migratiebrp.test.perf.isc.environment.TestEnvironment;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//@org.junit.Ignore
public class DumpQueuesTest {

    @Test
    public void test() {
        final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("classpath:test-beans.xml");
        context.registerShutdownHook();
        final TestEnvironment environment = context.getBean(TestEnvironment.class);

        final File directory = new File("p:\\queue-dump");
        directory.mkdirs();

        environment.dumpQueues(directory);
    }
}
