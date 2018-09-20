/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration("classpath:inmemory-datasource-missing-properties.xml")
public class VoaMainMissingPropertiesTest extends AbstractVoaMainTest {

    @Test
    public void test() {
        setupSecurityManager(6);
        final String[] args = new String[] {};
        boolean systemExit = false;
        try {
            createPassw();
            VoaMain.setConfiguratieFiles(new String[] { "classpath:voisc.xml", "classpath:voisc-jms-mocked.xml",
                    "classpath:voisc-db.xml", "classpath:voisc-db-cache.xml",
                    "classpath:voisc-datasource-inmemory.xml", "classpath:isc-db.xml",
                    "classpath:isc-db-datasource-embedded.xml", });
            VoaMain.main(args);
        } catch (final IOException e) {
            fail("Er zou geen IOException moeten optreden.");
        } catch (final SecurityException se) {
            systemExit = true;
        }
        assertTrue("System exit door een missende property.", systemExit);
    }
}
